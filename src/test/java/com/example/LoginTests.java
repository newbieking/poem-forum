package com.example;

import com.example.pojo.UserLogin;
import com.example.service.LoginService;
import com.example.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
public class LoginTests {
    private UserLogin userLogin =
            new UserLogin(UUID.randomUUID(), "test", "test", Date.from(Instant.now()));

    @Autowired
    LoginService loginService;


    /**
     * first login
     */
    @Test
    void firstLogin() {
        if (loginService.isLogin(userLogin.getId().toString())) {
            System.out.println("already login");
        } else {
            String token = loginService.login(userLogin);
            String[] userIdToken = LoginService.parseToken(token);
            System.out.println("login: " + token);
            boolean b = loginService.reLogin(userIdToken[0], userIdToken[1]);
            assert b;
            boolean logout = loginService.logout(userIdToken[0], userIdToken[1]);
            assert logout;
        }
    }
}

