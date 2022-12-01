package com.example.eproject.controller;

import com.example.eproject.dto.AdmissionsDto;
import com.example.eproject.dto.request.SignupRequest;
import com.example.eproject.service.AdmissionsService;
import com.example.eproject.service.MessageResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "admissions")
@Slf4j
public class AdmissionsController {
    final AdmissionsService admissionsService;
    final MessageResourceService messageResourceService;

    @GetMapping("list")
    public String admission(Model model) {
        AdmissionsDto admissionsDto = new AdmissionsDto();
        model.addAttribute("admissionsDto", admissionsDto);
        return "layout/admissions";
    }

    @PostMapping("signup")
    public String register(
            @Valid @ModelAttribute AdmissionsDto admissionsDto,
            BindingResult result,
            Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "redirect:/admissions/list";
        }
        admissionsService.create(admissionsDto);
        model.addAttribute("admissionsDto", admissionsDto);
        return "layout/admissions";
    }
}
