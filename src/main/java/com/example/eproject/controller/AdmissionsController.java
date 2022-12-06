package com.example.eproject.controller;

import com.example.eproject.dto.AdmissionsDto;
import com.example.eproject.service.AdmissionsService;
import com.example.eproject.service.MessageResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "admissions")
@Slf4j
public class AdmissionsController {
    final AdmissionsService admissionsService;

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
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("alert", "Error");
            return "redirect:/admissions/list";
        }
        model.addAttribute("alert", "Success");
        admissionsService.create(admissionsDto);
        admissionsDto = new AdmissionsDto();
        model.addAttribute("admissionsDto", admissionsDto);
        return "redirect:/admissions/list";
    }
}
