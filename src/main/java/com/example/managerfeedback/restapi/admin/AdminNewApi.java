package com.example.managerfeedback.restapi.admin;

import com.example.managerfeedback.dto.MessageResponse;
import com.example.managerfeedback.dto.NewsDto;
import com.example.managerfeedback.entity.News;
import com.example.managerfeedback.repository.CategoryRepository;
import com.example.managerfeedback.service.CategoryService;
import com.example.managerfeedback.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("admin/api/news")
public class AdminNewApi {
    @Autowired
    NewsService newsService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping()
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<News>> getLists(){
        return ResponseEntity.ok(newsService.findAll());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getDetails(@PathVariable Integer id){
        Optional<News> optionalNews = newsService.findById(id);
        if (!optionalNews.isPresent()){
            ResponseEntity.badRequest().build();
        }
        optionalNews.get().setViews(optionalNews.get().getViews() + 1);
        newsService.save(optionalNews.get());
        return ResponseEntity.ok(optionalNews.get());
    }

    @PostMapping()
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody NewsDto newsDto, Authentication principal) {
        long adminId = Long.parseLong(principal.getName());
        newsService.create(newsDto, adminId);
        return ResponseEntity.ok(new MessageResponse("News has been added successfully!"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<News> update(@PathVariable Integer id, @RequestBody News news){
        Optional<News> optionalNews = newsService.findById(id);
        if ((!optionalNews.isPresent())){
            ResponseEntity.badRequest().build();
        }
        News existNews = optionalNews.get();

        existNews.setTitle(news.getTitle());
        existNews.setDescription(news.getDescription());
        existNews.setImg(news.getImg());
        existNews.setContent(news.getContent());
        existNews.setViews(news.getViews());
        existNews.setStatus(news.getStatus());
        existNews.setAuthor(news.getAuthor());
        existNews.setCategories(news.getCategories());
        return ResponseEntity.ok(newsService.save(existNews));
    }

    @PutMapping("/{id}/{keyword}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<News> updated(@PathVariable Integer id,@PathVariable String keyword , @RequestBody News news){
        Optional<News> optionalNews = newsService.findById(id);
        if ((!optionalNews.isPresent())){
            ResponseEntity.badRequest().build();
        }
        News existNews = optionalNews.get();

        if (keyword.equals("title")){
            existNews.setTitle(news.getTitle());
        } else if (keyword.equals("description")){
            existNews.setDescription(news.getDescription());
        } else if (keyword.equals("img")){
            existNews.setImg(news.getImg());
        } else if (keyword.equals("content")){
            existNews.setContent(news.getContent());
        } else if (keyword.equals("views")){
            existNews.setViews(news.getViews());
        } else if (keyword.equals("status")){
            existNews.setStatus(news.getStatus());
        } else if (keyword.equals("author")){
            existNews.setAuthor(news.getAuthor());
        } else if (keyword.equals("categories")){
            existNews.setCategories(news.getCategories());
        } else {
            ResponseEntity.badRequest();
            new RuntimeException("Error: keyword not true");
        }
        return ResponseEntity.ok(newsService.save(existNews));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        if ((!newsService.findById(id).isPresent())){
            ResponseEntity.badRequest().build();
        }
        newsService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
