package com.example;

import com.example.pojo.Poem;
import com.example.pojo.UserPoemCollect;
import com.example.service.PoemCollectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class PoemCollectTests {

    @Autowired
    PoemCollectService poemCollectService;

    @Test
    void testCollect() {
        UserPoemCollect userPoemCollect = new UserPoemCollect();
        userPoemCollect.setUserId("001");
        userPoemCollect.setPoemId(1L);
        UserPoemCollect save = poemCollectService.collectPoem(userPoemCollect);
        Assert.isTrue(save.getId().equals(userPoemCollect.getId()), "id not equal");

        List<Poem> collect = poemCollectService.findPoemCollectByUserId("001");
        Assert.isTrue(!collect.isEmpty(), "collect is empty");
    }
}
