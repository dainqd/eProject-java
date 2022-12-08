package com.example.eproject.controller;

import com.example.eproject.dto.*;
import com.example.eproject.service.EmailFollowService;
import com.example.eproject.service.EmailService;
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
public class HomeController {
    final ManagerService managerService;
    final FeedbackService feedbackService;
    final EmailFollowService emailFollowService;
    final EmailService emailService;

    @GetMapping("faculty")
    public String faculty(Model model) {
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

    @GetMapping("email")
    public String getEmail(Model model) {
        EmailFollowDto emailFollowDto = new EmailFollowDto();
        model.addAttribute("emailFollowDto", emailFollowDto);
        return "include/footer";
    }

    @PostMapping("email")
    public String postEmail(Model model,
                            @Valid @ModelAttribute EmailFollowDto emailFollowDto,
                            BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "/error/404";
            }
            emailFollowService.save(emailFollowDto);
            emailService.followEmail(emailFollowDto.getEmail());
            model.addAttribute("emailFollowDto", new EmailFollowDto());
//            Đoạn này muốn đang ở trang nào thì trả về trang đấy nhưng chưa tìm được giải pháp
//            Sẽ tìm giải pháp sau, tạm thời sau khi success thì đưa về trang chủ
            return "redirect:/";
        } catch (Exception e) {
            return "/error/404";
        }
    }
}
