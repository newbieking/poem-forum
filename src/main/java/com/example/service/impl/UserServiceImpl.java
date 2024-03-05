package com.example.service.impl;

import com.example.pojo.UserLogin;
import com.example.repositories.UserLoginRepository;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserLoginRepository userLoginRepository;

    @Override
    public String findUsernameByUserId(UUID userId) {
        String userName = null;
        Optional<UserLogin> user = userLoginRepository.findById(userId);
        if (user.isPresent()) {
            userName = user.get().getUsername();
        }
        return userName;
    }

    @Override
    public UserLogin findByUsername(String username) {
        return userLoginRepository.findByUsername(username);
    }

    @Override
    public UserLogin registerUser(UserLogin userLogin) {
        UserLogin user = userLoginRepository.findByUsername(userLogin.getUsername());
        if (user == null) {
            return userLoginRepository.save(userLogin);
        }
        return null;
    }
}
