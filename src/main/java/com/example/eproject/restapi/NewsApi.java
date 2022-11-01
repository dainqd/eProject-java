package com.example.eproject.restapi;

import com.example.eproject.entity.News;
import com.example.eproject.repository.CategoryRepository;
import com.example.eproject.service.CategoryService;
import com.example.eproject.service.NewsService;
import com.example.eproject.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
