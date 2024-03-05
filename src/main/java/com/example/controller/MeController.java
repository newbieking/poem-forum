package com.example.controller;

import com.example.pojo.Poem;
import com.example.service.LoginService;
import com.example.service.PoemCollectService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class MeController {

    @Autowired
    PoemCollectService poemCollectService;
    @Autowired
    UserService userService;

    @GetMapping("me")
    String me(
            @RequestParam("token") String token,
            Model model
    ) {
        String[] parsedToken = LoginService.parseToken(token);
        String userId = parsedToken[0];
        List<Poem> collect = poemCollectService.findPoemCollectByUserId(userId);
        String username = userService.findUsernameByUserId(UUID.fromString(userId));
        model.addAttribute("username", username);
        model.addAttribute("poems", collect);
        model.addAttribute("token", token);
        return "me";
    }
}
