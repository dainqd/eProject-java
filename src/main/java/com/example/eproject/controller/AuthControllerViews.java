package com.example.eproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("service")
@RequiredArgsConstructor
@Slf4j
public class AuthControllerViews {
    @GetMapping("login")
    public String login() {
//        AuthController authController
//        model.addAttribute("loginFormDto", loginFormDto);
        return "auth/login";
    }

    @GetMapping("register")
    public String register() {
//        AuthController authController
//        model.addAttribute("loginFormDto", loginFormDto);
        return "auth/register";
    }

    @GetMapping("forgot-password")
    public String forgotPassword() {
//        AuthController authController
//        model.addAttribute("loginFormDto", loginFormDto);
        return "auth/forgot-password";
    }
}
