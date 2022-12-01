package com.example.eproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("views")
@Slf4j
public class HomeController {
    @GetMapping("faculty")
    public String faculty(Model model) {
        return "layout/faculty";
    }

    @GetMapping("addmission")
    public String addmission(Model model) {
        return "layout/addmission";
    }
}
