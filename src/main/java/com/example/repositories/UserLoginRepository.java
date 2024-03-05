package com.example.repositories;

import com.example.pojo.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserLoginRepository extends JpaRepository<UserLogin, UUID> {
    UserLogin findByUsername(String username);
}