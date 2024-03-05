package com.example.repositories;

import com.example.pojo.Poem;
import com.example.pojo.UserPoemCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserPoemCollectRepository extends JpaRepository<UserPoemCollect, String> {
    //    @Query(value = "select * from (select pc.poem_id id from poem_collect pc where pc.user_id = ?1) c left join poem p on p.id=c.id", nativeQuery = true)
    @Query(value = "select p from UserPoemCollect pc, Poem p where pc.userId = ?1 and  p.id=pc.poemId")
    List<Poem> findPoemByUserId(String userId);

    UserPoemCollect findByPoemIdAndUserId(Long poemId, String userId);
}