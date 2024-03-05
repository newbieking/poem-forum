package com.example.service;

import com.example.pojo.PoemComment;

import java.util.List;

/**
 * service for poem's comment
 */
public interface PoemCommentService {

    /**
     * find comment under the specified poem
     *
     * @param poemId poem's ID
     * @return the comments found
     */
    List<PoemComment> findCommentByPoemId(Long poemId);

    /**
     * add comment
     *
     * @param poemComment comment
     */
    PoemComment addComment(PoemComment poemComment);
}
