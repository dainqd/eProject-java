package com.example.eproject.restapi.admin;

import com.example.eproject.dto.NewsDto;
import com.example.eproject.entity.News;
import com.example.eproject.entity.User;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.service.NewsService;
import com.example.eproject.service.UserDetailsServiceImpl;
import com.example.eproject.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("admin/api/news")
public class AdminNewApi {
    @Autowired
    NewsService newsService;
    @Autowired
    MessageResourceService messageResourceService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping()
    public ResponseEntity<Page<News>> getLists(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                               @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                               @RequestParam(value = "status", required = false, defaultValue = "") Enums.NewsStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            return ResponseEntity.ok(newsService.findAllByStatus(status, pageable));
        }
        return ResponseEntity.ok(newsService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getDetails(@PathVariable("id") long id) {
        Optional<News> optionalNews = newsService.findById(id);
        if (!optionalNews.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        optionalNews.get().setViews(optionalNews.get().getViews() + 1);
        newsService.save(optionalNews.get());
        return ResponseEntity.ok(optionalNews.get());
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody NewsDto newsDto, Authentication principal) {
        String adminID = principal.getName();
        Optional<User> optionalUse = userDetailsService.findByUsername(adminID);
        if (!optionalUse.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = optionalUse.get();
        newsService.create(newsDto, user.getId());
        return ResponseEntity.ok(messageResourceService.getMessage("create.success"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<News> update(@PathVariable("id") long id, @RequestBody NewsDto newsDto, Authentication principal) {
        Optional<News> optionalNews = newsService.findById(id);
        if ((!optionalNews.isPresent())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        String adminID = principal.getName();
        Optional<User> optionalUse = userDetailsService.findByUsername(adminID);
        if (!optionalUse.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        User user = optionalUse.get();
        return ResponseEntity.ok(newsService.update(newsDto, user.getId()));
    }

    @PutMapping("/{id}/{keyword}")
    public ResponseEntity<News> updated(@PathVariable("id") long id, @PathVariable("keyword") String keyword, @RequestBody News news) {
        Optional<News> optionalNews = newsService.findById(id);
        if ((!optionalNews.isPresent())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        News existNews = optionalNews.get();

        switch (keyword) {
            case "title":
                existNews.setTitle(news.getTitle());
                break;
            case "description":
                existNews.setDescription(news.getDescription());
                break;
            case "img":
                existNews.setImg(news.getImg());
                break;
            case "content":
                existNews.setContent(news.getContent());
                break;
            case "views":
                existNews.setViews(news.getViews());
                break;
            case "status":
                existNews.setStatus(news.getStatus());
                break;
            case "author":
                existNews.setAuthor(news.getAuthor());
                break;
            case "categories":
                existNews.setCategories(news.getCategories());
                break;
            default:
                ResponseEntity.badRequest();
                break;
        }
        return ResponseEntity.ok(newsService.save(existNews));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String adminId = principal.getName();
        Optional<User> op = userDetailsService.findByUsername(adminId);
        if (!op.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = op.get();
        if ((!newsService.findById(id).isPresent())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        newsService.delete(id, user.getId());
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }
}
