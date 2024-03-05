package com.example.service.impl;

import com.example.pojo.PoemComment;
import com.example.repositories.PoemCommentRepository;
import com.example.service.PoemCommentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PoemCommentServiceImpl implements PoemCommentService {

    @NonNull
    final PoemCommentRepository poemCommentRepository;

    @Override
    public List<PoemComment> findCommentByPoemId(Long poemId) {
        return poemCommentRepository.findByPoemId(poemId);
    }

    @Override
    public PoemComment addComment(PoemComment poemComment) {
        return poemCommentRepository.save(poemComment);
    }
}
