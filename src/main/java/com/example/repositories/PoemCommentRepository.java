package com.example.repositories;

import com.example.pojo.PoemComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoemCommentRepository extends JpaRepository<PoemComment, Long> {
    List<PoemComment> findByPoemId(Long poemId);
}