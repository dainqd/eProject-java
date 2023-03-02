package com.example.eproject.controller;

import com.example.eproject.dto.*;
import com.example.eproject.service.FeedbackService;
import com.example.eproject.service.ManagerService;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("views")
@Slf4j
public class WebappController {
    final ManagerService managerService;
    final FeedbackService feedbackService;

    @GetMapping("faculty")
    public String faculty(Model model, @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                          @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<ManagerDto> managerDto = managerService.findAllByStatus(Enums.ManagerStatus.ACTIVE, pageable).map(ManagerDto::new);
        model.addAttribute("managerDto", managerDto);
        return "layout/faculty";
    }

    @GetMapping("gallery")
    public String gallery(Model model) {
        return "layout/gallery";
    }

    @GetMapping("contact")
    public String contact(Model model) {
        FeedbacksDto feedbacksDto = new FeedbacksDto();
        model.addAttribute("feedbacksDto", feedbacksDto);
        return "layout/contact";
    }

    @PostMapping("feedbacks")
    public String feedbacks(Model model,
                            @Valid @ModelAttribute FeedbacksDto feedbacksDto,
                            BindingResult result) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("isSuccess", false);
                return "redirect:/views/contact";
            }
            feedbackService.save(feedbacksDto);
            model.addAttribute("isSuccess", true);
            model.addAttribute("feedbacksDto", new FeedbacksDto());
            return "redirect:/views/contact";
        } catch (Exception e) {
            return "/error/404";
        }
    }

    @GetMapping("about")
    public String getList(Model model, @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                          @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<ManagerDto> managerDto = managerService.findAllByStatus(Enums.ManagerStatus.ACTIVE, pageable).map(ManagerDto::new);
            model.addAttribute("managerDto", managerDto);
            return "layout/about";
        } catch (Exception e) {
            return "/error/404";
        }
    }


}
