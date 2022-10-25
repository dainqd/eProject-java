package com.example.managerfeedback.restapi;

import com.example.managerfeedback.entity.News;
import com.example.managerfeedback.repository.CategoryRepository;
import com.example.managerfeedback.service.CategoryService;
import com.example.managerfeedback.service.NewsService;
import com.example.managerfeedback.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/news")
public class NewsApi {
    @Autowired
    NewsService newsService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping()
    public ResponseEntity<List<News>> getList(){
        return ResponseEntity.ok(newsService.getListByStatus(Enums.NewsStatus.ACTIVE));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetail(@PathVariable long id){
        Optional<News> optionalNews = newsService.getListByIdAndStatus(id,Enums.NewsStatus.ACTIVE);
        if (!optionalNews.isPresent()){
            ResponseEntity.badRequest().build();
        }
        optionalNews.get().setViews(optionalNews.get().getViews() + 1);
        newsService.save(optionalNews.get());
        return ResponseEntity.ok(optionalNews.get());
    }
}
