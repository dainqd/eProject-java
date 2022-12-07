package com.example.eproject.controller;

import com.example.eproject.dto.AdmissionsDto;
import com.example.eproject.dto.CourseDto;
import com.example.eproject.dto.CourseRegisterDto;
import com.example.eproject.dto.ManagerDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("views")
@Slf4j
public class HomeController {
    final ManagerService managerService;

    @GetMapping("faculty")
    public String faculty(Model model) {
        return "layout/faculty";
    }

    @GetMapping("gallery")
    public String gallery(Model model) {
        return "layout/gallery";
    }

//    @GetMapping("about")
//    public String about(Model model) {
//        return "layout/about";
//    }

    @GetMapping("contact")
    public String contact(Model model) {
        return "layout/contact";
    }

//    @GetMapping("manager")
//    public String manager(Model model) {
//        ManagerDto managerDto = new ManagerDto();
//        model.addAttribute("managerDto", managerDto);
//        return "layout/about";
//    }

    @GetMapping("about")
    public String getList(Model model, @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                          @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<ManagerDto> managerDto = managerService.findAllByStatus(Enums.ManagerStatus.ACTIVE, pageable).map(ManagerDto::new);
            model.addAttribute("managerDto", managerDto);
            return "layout/about";
        } catch (Exception e) {
            return "layout/about";
        }
    }
}
