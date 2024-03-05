package com.example;

import com.example.service.RecentPoemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;

@SpringBootTest
public class RecentTests {

    @Autowired
    RecentPoemService recentPoemService;

    private String userId = "001";

    @Test
    void testUpdateRecentPoems() {
        ArrayList<Long> ids = new ArrayList<>();
        for (long i = 65; i < 100; i++) {
            ids.add(i);
        }
        recentPoemService.updateRecentVisitedPoems(userId, ids);
    }

    @Test
    void testUpdateRecentSearches() {
        ArrayList<String> searches = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            searches.add(String.valueOf(i));
        }
        searches.add("李白");
        searches.add("静夜思");
        searches.add("黄河");
        recentPoemService.updateRecentSearch(userId, searches);
    }

}
