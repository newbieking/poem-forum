package com.example.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.consts.Const;
import com.example.pojo.Poem;
import com.example.pojo.PoemDoc;
import com.example.repositories.PoemDocRepository;
import com.example.repositories.PoemRepository;
import com.example.service.PoemCollectService;
import com.example.service.PoemService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PoemServiceImpl implements PoemService {
    @NonNull PoemRepository poemRepository;
    @NonNull PoemDocRepository poemDocRepository;
    @NonNull ElasticsearchClient elasticsearchClient;
    @NonNull StringRedisTemplate stringRedisTemplate;
    @NonNull PoemCollectService poemCollectService;


    @Override
    public void setUpPoemTableData() {
        ArrayList<Poem> poems = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Poem poem = new Poem();
            poem.setTitle("title " + i);
            poem.setAuthor("author " + i);
            poem.setContent("content " + i);
            poems.add(poem);
        }
        poemRepository.saveAll(poems);
    }

    @Override
    public void loadData() throws IOException {
        List<Poem> all = poemRepository.findAll();
        List<BulkOperation> bulkOperations = all.stream().map(poem ->
                BulkOperation.of(b ->
                        b.index(i ->
                                i.id(String.valueOf(poem.getId())).document(poem)
                        ))
        ).toList();

        BulkResponse bulkResponse = elasticsearchClient.bulk(b -> b
                .index(Const.POEM_DOC)
                .operations(bulkOperations));
        bulkResponse.items().forEach(i ->
                System.out.printf("result: %s\n", i.result())
        );
        System.err.printf("errors: %s\n", bulkResponse.errors());
    }

    public Page<PoemDoc> findPoemDoc(Pageable pageable) {
        return poemDocRepository.findAll(pageable);
    }

    @Override
    public Page<PoemDoc> searchPoemDoc(String searchText, Pageable pageable) throws IOException {
        SearchResponse<PoemDoc> search = elasticsearchClient.search(builder -> builder
                        .index(Const.POEM_DOC)
                        .query(q -> q
                                .multiMatch(mu -> mu
                                        .query(searchText)
                                        .fields(Const.PoemDoc.title, Const.PoemDoc.author, Const.PoemDoc.content)))
                        .from(pageable.getPageNumber())
                        .size(pageable.getPageSize()),
                PoemDoc.class
        );

        List<Hit<PoemDoc>> hits = search.hits().hits();

        // add rank score for every search hit
        hits.forEach(item -> {
            stringRedisTemplate.opsForZSet().incrementScore(
                    Const.Redis.TOP_RANK,
                    String.valueOf(item.id()),
                    .1);
        });

        List<PoemDoc> list = hits.stream().map(Hit::source).toList();
        PageImpl<PoemDoc> poemDocs =
                new PageImpl<>(list);
        return poemDocs;
    }


    @Override
    public List<Poem> topPoems() {
        Set<String> top = stringRedisTemplate.opsForZSet().reverseRange(
                Const.Redis.TOP_RANK,
                0,
                Const.Redis.TOP_RANK_DEFAULT_SIZE);

        if (null != top) {
            List<Long> ids = top.stream()
                    .map(Long::parseLong)
                    .toList();
            List<Poem> all = poemRepository.findAllById(ids);
            all.sort(Comparator.comparingInt(o -> ids.indexOf(o.getId())));
            return all.subList(0, 10);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Poem> recommendPoems(String userId) throws IOException {
        Set<String> range =
                stringRedisTemplate.opsForZSet().range(
                        Const.Redis.recentSearch(userId),
                        0,
                        Const.Redis.RECENT_SEARCH_DEFAULT_SIZE);
        String txt;
        if (range == null) {
            txt = "";
        } else {
            txt = String.join(" ", range);
        }
        SearchResponse<PoemDoc> search = elasticsearchClient.search(s -> s
                .index(Const.POEM_DOC)
                .query(q -> q
                        .moreLikeThis(m -> m
                                .fields(Const.PoemDoc.all)
                                .like(l -> l
                                        .text(txt))
                                .minTermFreq(1))
                ).size(10), PoemDoc.class);
        List<Long> list =
                search.hits().hits().stream().map(Hit::id).map(Long::parseLong).toList();

        PageRequest pageRequest = PageRequest.of(0, 10);
        return list.isEmpty() ?
                poemRepository.findAll(pageRequest).getContent() :
                poemRepository.findAllById(list);
    }

    @Override
    public Optional<Poem> findPoemById(Long id) {
        return poemRepository.findById(id);
    }
}
