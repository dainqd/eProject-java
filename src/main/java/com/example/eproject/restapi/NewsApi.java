package com.example.eproject.restapi;

import com.example.eproject.entity.News;
import com.example.eproject.repository.CategoryRepository;
import com.example.eproject.service.CategoryService;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.service.NewsService;
import com.example.eproject.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/news")
public class NewsApi {
    @Autowired
    NewsService newsService;
    @Autowired
    MessageResourceService messageResourceService;

    @GetMapping()
    public ResponseEntity<Page<News>> getList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                              @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(newsService.getListByStatus(Enums.NewsStatus.ACTIVE, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetail(@PathVariable long id) {
        Optional<News> optionalNews = newsService.getListByIdAndStatus(id, Enums.NewsStatus.ACTIVE);
        if (!optionalNews.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        optionalNews.get().setViews(optionalNews.get().getViews() + 1);
        newsService.save(optionalNews.get());
        return ResponseEntity.ok(optionalNews.get());
    }
}
