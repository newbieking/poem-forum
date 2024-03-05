package com.example.service;


import com.example.pojo.Poem;
import com.example.pojo.UserPoemCollect;

import java.util.List;

public interface PoemCollectService {

    /**
     * collect user's favorite poem
     *
     * @param userPoemCollect collect
     * @return collection be added
     */
    UserPoemCollect collectPoem(UserPoemCollect userPoemCollect);

    List<Poem> findPoemCollectByUserId(String userId);

    boolean isCollectedPoem(String userId, Long poemId);


}
