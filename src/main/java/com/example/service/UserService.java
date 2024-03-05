package com.example.service;

import com.example.pojo.UserLogin;

import java.util.UUID;

public interface UserService {


    String findUsernameByUserId(UUID userId);

    UserLogin findByUsername(String username);

    UserLogin registerUser(UserLogin userLogin);
}
