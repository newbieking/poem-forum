package com.example.service.impl;

import com.example.pojo.PoemDoc;
import com.example.service.RecentPoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static com.example.consts.Const.Redis.*;

@Service
public class RecentPoemServiceImpl implements RecentPoemService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void updateRecentVisitedPoems(String userId, List<Long> recentPoems) {
        String recentVisit = recentVisit(userId);
        recentPoems.forEach(id -> {
            stringRedisTemplate.opsForZSet().add(recentVisit, String.valueOf(id), Instant.now().toEpochMilli());
        });
        Long size = stringRedisTemplate.opsForZSet().size(recentVisit);
        if (size != null && RECENT_VISIT_DEFAULT_SIZE < size) {
            stringRedisTemplate.opsForZSet().removeRange(recentVisit, 0, RECENT_VISIT_DEFAULT_SIZE);
        }
    }

    @Override
    public void updateRecentSearch(String userId, List<String> recentSearches) {
        String recentSearch = recentSearch(userId);
        recentSearches.forEach(search -> {
            stringRedisTemplate.opsForZSet().add(recentSearch, search, Instant.now().toEpochMilli());
        });
        Long size = stringRedisTemplate.opsForZSet().size(recentSearch);
        if (size != null && RECENT_SEARCH_DEFAULT_SIZE < size) {
            stringRedisTemplate.opsForZSet().removeRange(recentSearch, 0, size - RECENT_VISIT_DEFAULT_SIZE);
        }
    }
}
