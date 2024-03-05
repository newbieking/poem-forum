package com.example.controller;

import com.example.pojo.PoemComment;
import com.example.service.LoginService;
import com.example.service.PoemCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.Instant;

@Controller
public class CommentController {

    @Autowired
    PoemCommentService poemCommentService;

    @PostMapping("comment")
    String comment(
            @RequestParam("token") String token,
            @RequestParam("poemId") Long poemId,
            @RequestParam("comment") String comment,
            RedirectAttributes redirectAttributes
    ) {
        String[] parsedToken = LoginService.parseToken(token);
        String userId = parsedToken[0];
        PoemComment poemComment = new PoemComment();
        poemComment.setContent(comment);
        poemComment.setPoemId(poemId);
        poemComment.setCreateTime(Date.from(Instant.now()));
        poemComment.setUserId(userId);
        poemCommentService.addComment(poemComment);
        redirectAttributes.addAttribute("token", token);
        return "redirect:/poem/" + poemId;
    }
}
