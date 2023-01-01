package com.example.eproject.controller;

import com.example.eproject.dto.*;
import com.example.eproject.entity.Category;
import com.example.eproject.service.*;
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
@RequestMapping("")
@Slf4j
public class HomeController {
    final ManagerService managerService;
    final EventsService eventsService;
    final CourseService courseService;
    final CourseRegisterService courseRegisterService;
    final NewsService newsService;
    final CategoryService categoryService;

    @GetMapping("")
    public String getList(Model model, @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                          @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

            Page<CourseDto> courseDto = courseService.findAllByStatusNoDelete(Enums.CourseStatus.ACTIVE, pageable).map(CourseDto::new);
            model.addAttribute("courseDto", courseDto);

            CourseRegisterDto courseRegisterDto = new CourseRegisterDto();
            model.addAttribute("courseRegisterDto", courseRegisterDto);

            Page<EventsDto> eventsDto = eventsService.findAllByStatusNoDelete(Enums.EventsStatus.ACTIVE, pageable).map(EventsDto::new);
            model.addAttribute("eventsDto", eventsDto);

            Page<ManagerDto> managerDto = managerService.findAllByStatus(Enums.ManagerStatus.ACTIVE, pageable).map(ManagerDto::new);
            model.addAttribute("managerDto", managerDto);

            pageable = PageRequest.of(page, 4, Sort.by("id").descending());
            Page<NewsDto> newsDtos = newsService.findAllByStatus(Enums.NewsStatus.ACTIVE, pageable).map(NewsDto::new);
            model.addAttribute("newsDtos", newsDtos);

            Page<Category> categories = categoryService.findAllByStatus(Enums.CategoryStatus.ACTIVE, pageable);
            model.addAttribute("categories", categories);

            return "/v1/index";
        } catch (Exception e) {
            return "/error/404";
        }
    }

    @PostMapping("courses/register")
    public String register(@Valid @ModelAttribute CourseRegisterDto courseRegisterDto,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("alert", "Error");
            return "redirect:/courses/list";
        }
        courseRegisterService.save(courseRegisterDto);
        model.addAttribute("courseRegisterDto", new CourseRegisterDto());
        return "redirect:/courses/list";
    }
}
