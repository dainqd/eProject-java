//package com.example.eproject.controller;
//
//import com.example.eproject.dto.CourseDto;
//import com.example.eproject.dto.CourseRegisterDto;
//import com.example.eproject.dto.EventsDto;
//import com.example.eproject.dto.NewsDto;
//import com.example.eproject.entity.Category;
//import com.example.eproject.service.*;
//import com.example.eproject.util.Enums;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("home")
//@Slf4j
//public class HomeController {
//    final ManagerService managerService;
//    final EventsService eventsService;
//    final CourseService courseService;
//    final CourseRegisterService courseRegisterService;
//    final NewsService newsService;
//    final CategoryService categoryService;
//
//    @GetMapping("list")
//    public String getList(Model model, @RequestParam(value = "page", required = false, defaultValue = "0") int page,
//                          @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
//        try {
//            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
//
//            Page<CourseDto> courseDto = courseService.findAllByStatusNoDelete(Enums.CourseStatus.ACTIVE, pageable).map(CourseDto::new);
//            model.addAttribute("courseDto", courseDto);
//
//            CourseRegisterDto courseRegisterDto = new CourseRegisterDto();
//            model.addAttribute("courseRegisterDto", courseRegisterDto);
//
//            Page<EventsDto> eventsDto = eventsService.findAllByStatusNoDelete(Enums.EventsStatus.ACTIVE, pageable).map(EventsDto::new);
//            model.addAttribute("eventsDto", eventsDto);
//
//            Page<NewsDto> newsDtos = newsService.findAllByStatus(Enums.NewsStatus.ACTIVE, pageable).map(NewsDto::new);
//            model.addAttribute("newsDtos", newsDtos);
//
//            Page<Category> categories = categoryService.findAllByStatus(Enums.CategoryStatus.ACTIVE, pageable);
//            model.addAttribute("categories", categories);
//            return "index";
//        } catch (Exception e) {
//            return "/error/404";
//        }
//    }
//}
