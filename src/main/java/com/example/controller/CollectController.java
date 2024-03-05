package com.example.controller;

import com.example.pojo.UserPoemCollect;
import com.example.service.LoginService;
import com.example.service.PoemCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CollectController {


    @Autowired
    PoemCollectService poemCollectService;

    @PostMapping("collect")
    String collect(
            @RequestParam("poemId") Long poemId,
            @RequestParam("token") String token,
            RedirectAttributes redirectAttributes
    ) {
        String[] parsedToken = LoginService.parseToken(token);
        String userId = parsedToken[0];
        UserPoemCollect collect = new UserPoemCollect();
        collect.setUserId(userId);
        collect.setPoemId(poemId);
        poemCollectService.collectPoem(collect);
        redirectAttributes.addAttribute("token", token);
        return "redirect:/poem/" + poemId;
    }
}
