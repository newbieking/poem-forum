package com.example.service.impl;

import com.example.consts.Const;
import com.example.pojo.UserLogin;
import com.example.repositories.UserLoginRepository;
import com.example.service.LoginService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.example.consts.Const.Redis.loginUserId;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    @NonNull StringRedisTemplate stringRedisTemplate;
    private final UserLoginRepository userLoginRepository;

    @Override
    public boolean reLogin(String userId, String token) {
        if (verifyToken(userId, token)) {
            return null != stringRedisTemplate.expire(
                    loginUserId(userId),
                    Duration.ofSeconds(Const.Redis.LOGIN_DEFAULT_EXPIRED_TIME));
        }
        return false;
    }

    private boolean verifyToken(String userId, String token) {
        String tokenLoaded = stringRedisTemplate.opsForValue().get(loginUserId(userId));
        if (tokenLoaded != null) {
            return tokenLoaded.equals(token);
        }
        return false;
    }

    @Override
    public String login(UserLogin userLogin) {
        String token = null;
        if (verifyUser(userLogin)) {
            String tailToken = stringRedisTemplate.opsForValue()
                    .get(loginUserId(String.valueOf(userLogin.getId())));
            if (tailToken == null) {
                tailToken = UUID.randomUUID().toString();
                stringRedisTemplate.opsForValue().set(
                        loginUserId(String.valueOf(userLogin.getId())),
                        tailToken,
                        Const.Redis.LOGIN_DEFAULT_EXPIRED_TIME,
                        TimeUnit.SECONDS);
            }
            token = userLogin.getId() + "." + tailToken;
        }
        return token;
    }

    private boolean verifyUser(UserLogin userLogin) {
        UserLogin user = userLoginRepository.findByUsername(userLogin.getUsername());
        if (user.getPassword().equals(userLogin.getPassword())) {
            userLogin.setId(user.getId());
            userLogin.setCreateTime(user.getCreateTime());
            return true;
        }
        return false;
    }

    @Override
    public boolean isLogin(String userId) {
        String s = stringRedisTemplate.opsForValue().get(loginUserId(userId));
        return null != s;
    }

    @Override
    public boolean logout(String userId, String token) {
        if (verifyToken(userId, token)) {
            return null != stringRedisTemplate.expire(loginUserId(userId), Duration.ZERO);
        }
        return false;
    }


}
