package com.example.eproject.controller;

import com.example.eproject.dto.NewsDto;
import com.example.eproject.entity.Category;
import com.example.eproject.service.CategoryService;
import com.example.eproject.service.NewsService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "blog-home")
@Slf4j
public class BlogHomeController {
    final NewsService newsService;
    final CategoryService categoryService;

    @GetMapping("list")
    public String getList(Model model, @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                          @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<NewsDto> newsDtos = newsService.findAllByStatus(Enums.NewsStatus.ACTIVE, pageable).map(NewsDto::new);
            model.addAttribute("newsDtos", newsDtos);
            Page<Category> categories = categoryService.findAllByStatus(Enums.CategoryStatus.ACTIVE, pageable);
            model.addAttribute("categories", categories);
            return "v1/blog_news/blog-home";
        } catch (Exception e) {
            return "/error/404";
        }
    }

    @GetMapping("detail/{id}")
    public String getDetail(Model model, @PathVariable("id") long id) {
        try {
            Optional<NewsDto> newsDtosOptional = newsService.findByIdAndStatus(id, Enums.NewsStatus.ACTIVE).map(NewsDto::new);
            if (!newsDtosOptional.isPresent()) {
                return "/error/404";
            }
            model.addAttribute("newsDto", newsDtosOptional.get());
            List<String> continents = newsDtosOptional.get().getTag();
            model.addAttribute("continents", continents);
            model.addAttribute("newsDtos", newsService.findByStatus(Enums.NewsStatus.ACTIVE));
            model.addAttribute("categories", categoryService.findAllByStatus(Enums.CategoryStatus.ACTIVE));
            System.out.println(continents);
            return "v1/blog_news/detail";
        } catch (Exception e) {
            return "/error/404";
        }
    }
}
