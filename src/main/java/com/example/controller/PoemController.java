package com.example.controller;

import com.example.pojo.Poem;
import com.example.pojo.PoemComment;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PoemController {

    @Autowired
    PoemService poemService;

    @Autowired
    PoemCommentService poemCommentService;

    @Autowired
    UserService userService;

    @Autowired
    RecentPoemService recentPoemService;

    @Autowired
    PoemCollectService poemCollectService;

    @GetMapping("poem/{poemId}")
    public String poem(@PathVariable Long poemId,
                       @RequestParam("token") String token,
                       Model model) {
        String[] parsedToken = LoginService.parseToken(token);
        String tokenId = parsedToken[0];
        Optional<Poem> poem = poemService.findPoemById(poemId);

        List<PoemComment> comments = poemCommentService.findCommentByPoemId(poemId);
        if (poem.isEmpty()) {
            model.addAttribute("error", "未找到相关的古诗词");
        } else {
            Poem p = poem.get();
            model.addAttribute("poem", p);
            ArrayList<Long> ids = new ArrayList<>();
            ids.add(p.getId());
            recentPoemService.updateRecentVisitedPoems(tokenId, ids);
        }
        comments.forEach(comment -> {
            String userId = comment.getUserId();
            String username = userService.findUsernameByUserId(UUID.fromString(userId));
            comment.setUserId(username);
        });
        boolean collected = poemCollectService.isCollectedPoem(tokenId, poemId);
        model.addAttribute("collected", collected);
        model.addAttribute("comments", comments);
        model.addAttribute("token", token);
        return "poem";
    }
}
