package com.example;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.consts.Const;
import com.example.pojo.Poem;
import com.example.pojo.PoemDoc;
import com.example.pojo.UserPoemCollect;
import com.example.repositories.PoemDocRepository;
import com.example.service.PoemService;
import org.apache.http.Consts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class PoemForumApplicationTests {

    @Autowired
    PoemService poemService;
    @Autowired
    PoemDocRepository poemDocRepository;
    @Autowired
    ElasticsearchClient elasticsearchClient;

    @Test
    void setupPoemData() {
        poemService.setUpPoemTableData();
    }

    @Test
    void getAllDoc() {
        System.out.println(poemDocRepository.findAll(Pageable.ofSize(10)).getContent());
    }

    @Test
    void searchPoem() throws IOException {
        String queryText = "4";
        SearchResponse<PoemDoc> search = elasticsearchClient.search(s -> s
                        .index("poem_collect")
                        .query(q -> q
                                .multiMatch(mu -> mu
                                        .fields("title", "author", "content")
                                        .query(queryText))
                        )
                ,
                PoemDoc.class
        );

        System.out.println("[TAG]" + search.hits().hits().stream().map(Hit::source).toList());

    }

    @Test
    public void testSearchPoem() throws IOException {
        String queryText = "title";
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<PoemDoc> poemDocs = poemService.searchPoemDoc(queryText, pageRequest);
        System.out.println(poemDocs.getContent());
    }

    @Test
    public void moreLikeThis() throws IOException {
        String txt = "望庐山瀑布 静夜思 杜甫";
        SearchResponse<PoemDoc> search = elasticsearchClient.search(s -> s
                .query(q -> q
                        .moreLikeThis(m -> m
                                .fields(Const.PoemDoc.all)
                                .like(l -> l
                                        .text(txt))
                                .minTermFreq(1))
                ).size(10), PoemDoc.class);
        List<PoemDoc> list = search.hits().hits().stream().map(Hit::source).toList();
        System.out.println(list.size());
        System.out.println(list);
    }

    @Test
    void testTopPoem() throws IOException {
        Page<PoemDoc> poemDocs =
                poemService.searchPoemDoc("梨花", PageRequest.of(0, 3));
        System.out.println(poemDocs.getContent());
        List<Poem> poems = poemService.topPoems();
        System.out.println(poems);
    }

    @Test
    void testRecommend() throws IOException {
        List<Poem> poems = poemService.recommendPoems("001");
        Assert.isTrue(!poems.isEmpty(), "empty poems");
        System.out.println(poems);
    }
}
