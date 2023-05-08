package com.example.eproject.controller;

import com.example.eproject.dto.AdmissionsDto;
import com.example.eproject.dto.CourseDto;
import com.example.eproject.dto.CourseRegisterDto;
import com.example.eproject.dto.EventsDto;
import com.example.eproject.service.CourseRegisterService;
import com.example.eproject.service.CourseService;
import com.example.eproject.service.EventsService;
import com.example.eproject.service.MessageResourceService;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "courses")
@Slf4j
public class CourseController {
    final CourseService courseService;
    final CourseRegisterService courseRegisterService;
    final EventsService eventsService;

    @GetMapping("list")
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
            return "v1/course/list";
        } catch (Exception e) {
            return "/error/404";
        }
    }

    @GetMapping("detail/{id}")
    public String getDetail(Model model, @PathVariable("id") long id) {
        try {
            Optional<CourseDto> courseDtoOptional = courseService.findByIdAndStatus(id, Enums.CourseStatus.ACTIVE).map(CourseDto::new);
            if (!courseDtoOptional.isPresent()) {
                return "/error/404";
            }
            model.addAttribute("course", courseDtoOptional.get());
            List<String> continents = courseDtoOptional.get().getOutline();
            model.addAttribute("continents", continents);
            CourseRegisterDto courseRegisterDto = new CourseRegisterDto();
            model.addAttribute("courseRegisterDto", courseRegisterDto);
            return "v1/course/detail";
        } catch (Exception e) {
            return "/error/404";
        }
    }

    @PostMapping("signup")
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
