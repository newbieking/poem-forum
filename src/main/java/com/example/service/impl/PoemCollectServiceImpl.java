package com.example.service.impl;

import com.example.pojo.Poem;
import com.example.pojo.UserPoemCollect;
import com.example.repositories.UserPoemCollectRepository;
import com.example.service.PoemCollectService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PoemCollectServiceImpl implements PoemCollectService {
    @NonNull UserPoemCollectRepository userPoemCollectRepository;

    @Override
    public UserPoemCollect collectPoem(UserPoemCollect userPoemCollect) {
        return userPoemCollectRepository.save(userPoemCollect);
    }

    @Override
    public List<Poem> findPoemCollectByUserId(String userId) {
        return userPoemCollectRepository.findPoemByUserId(userId);
    }

    @Override
    public boolean isCollectedPoem(String userId, Long poemId) {
        return null != userPoemCollectRepository.findByPoemIdAndUserId(poemId, userId);
    }

}
