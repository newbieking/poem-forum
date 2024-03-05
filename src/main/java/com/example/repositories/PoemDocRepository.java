package com.example.repositories;

import com.example.pojo.PoemDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PoemDocRepository extends ElasticsearchRepository<PoemDoc, Long> {
}
