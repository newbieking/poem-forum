package com.example;


import com.example.consts.Const;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTests {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    String userId = "001";

    String key = Const.Redis.LOGIN_PREFIX + ":" + userId;

    @Test
    void testLogin() {
        stringRedisTemplate.opsForValue()
                .set(key,
                        "token",
                        24,
                        TimeUnit.HOURS);
    }

    @Test
    void testLoginSession() {
        String token = stringRedisTemplate.opsForValue().get(key+"a");
        if (token == null
                || token.isEmpty()) {
            System.out.printf("token is invalid: %s\n", token);
        } else {
            System.out.printf("got %s\n", token);
        }
    }

    @Test
    void testExp() {
        Long expire = stringRedisTemplate.getExpire(key);
        System.out.println(expire);
    }
}
