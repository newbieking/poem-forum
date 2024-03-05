package com.example.controller;

import com.example.annotation.MustLogin;
import com.example.pojo.Poem;
import com.example.service.LoginService;
import com.example.service.PoemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {
    @NonNull PoemService poemService;


    @GetMapping("home")
    @MustLogin
    public String index(Model model,
                        @RequestParam("token") String token) throws IOException {
        String[] parsedToken = LoginService.parseToken(token);
        List<Poem> poems = poemService.recommendPoems(parsedToken[0]);
        List<Poem> topPoems = poemService.topPoems();
        model.addAttribute("poems", poems);
        model.addAttribute("token", token);
        model.addAttribute("topPoems", topPoems);
        return "home";
    }
}
