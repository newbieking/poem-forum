package com.example.service;

import com.example.pojo.Poem;
import com.example.pojo.PoemDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * poem service
 */
public interface PoemService {

    /**
     * init the table data for database
     */
    void setUpPoemTableData();


    void loadData() throws IOException;

    /**
     * find all poem doc
     *
     * @param pageable page desc
     * @return poem page
     */
    Page<PoemDoc> findPoemDoc(Pageable pageable);

    /**
     * search by poem title, author, content
     *
     * @param searchText text for search
     * @param pageable   page desc
     * @return poem page
     * @throws IOException thrown by ElasticsearchClient::search
     */
    Page<PoemDoc> searchPoemDoc(String searchText, Pageable pageable) throws IOException;


    /**
     * get the top poems
     *
     * @return top poems
     */
    List<Poem> topPoems();

    /**
     * find the recommended poems
     *
     * @param userId user's ID
     * @return recommended poems
     */
    List<Poem> recommendPoems(String userId) throws IOException;

    Optional<Poem> findPoemById(Long id);


}
