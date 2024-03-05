package com.example.controller;

import com.example.pojo.UserLogin;
import com.example.service.LoginService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.util.Date;

@Controller
public class RegisterController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @GetMapping("register")
    String register() {
        return "register";
    }

    @PostMapping("register")
    String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        UserLogin userLogin = new UserLogin(null, username, password, Date.from(Instant.now()));
        UserLogin save = userService.registerUser(userLogin);
        if (save == null) {
            model.addAttribute("error", "用户名重复");
            return "register";
        }
        String token = loginService.login(userLogin);
        if (token != null) {
            redirectAttributes.addAttribute("token", token);
            return "redirect:home";
        } else {
            model.addAttribute("error", "用户信息错误");
            return "register";
        }
    }
}
