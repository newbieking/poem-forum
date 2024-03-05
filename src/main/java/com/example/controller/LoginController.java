package com.example.controller;

import com.example.pojo.UserLogin;
import com.example.service.LoginService;
import com.example.service.PoemService;
import com.example.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    PoemService poemService;

    @Autowired
    UserService userService;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        UserLogin userLogin = new UserLogin(null, username, password, null);
        String token = loginService.login(userLogin);

        if (token != null) {
            redirectAttributes.addAttribute("token", token);
            return "redirect:home";
        } else {
            model.addAttribute("error", "用户信息错误");
            return "login";
        }
    }
}
