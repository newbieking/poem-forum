package com.example;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import com.example.consts.Const;
import com.example.pojo.Poem;
import com.example.repositories.PoemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class EsSetUpTests {

    @Autowired
    ElasticsearchClient elasticsearchClient;

    @Autowired
    PoemRepository poemRepository;

    @Test
    void loadData() throws IOException {
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
}
