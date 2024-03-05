package com.example;

import com.example.pojo.PoemComment;
import com.example.repositories.PoemCommentRepository;
import com.example.service.PoemCommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class PoemCommentTests {

    @Autowired
    PoemCommentRepository poemCommentRepository;

    @Autowired
    PoemCommentService poemCommentService;

    @Test
    void testCRUD() {
        PoemComment poemComment = new PoemComment();
        poemComment.setPoemId(1L);
        poemComment.setUserId("dummy userId");
        poemComment.setContent("dummy comment");
        poemComment.setCreateTime(Date.from(Instant.now()));

        PoemComment save = poemCommentService.addComment(poemComment);
        Assert.isTrue(Objects.equals(poemComment.getId(), save.getId()), "id not equal");

        List<PoemComment> commentByPoemId = poemCommentService.findCommentByPoemId(1L);
        Assert.isTrue(!commentByPoemId.isEmpty(), "no comment found");
    }

}
