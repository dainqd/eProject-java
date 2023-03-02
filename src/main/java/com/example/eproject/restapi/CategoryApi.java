package com.example.eproject.restapi;

import com.example.eproject.entity.Category;

import com.example.eproject.service.CategoryService;
import com.example.eproject.service.MessageResourceService;
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

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/category")
public class CategoryApi {
    @Autowired
    CategoryService categoryService;
    @Autowired
    MessageResourceService messageResourceService;

    @GetMapping()
    public ResponseEntity<Page<Category>> getList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                              @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(categoryService.findAllByStatus(Enums.CategoryStatus.ACTIVE, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetail(@PathVariable long id) {
        Optional<Category> optionalCategory = categoryService.findByIdAndStatus(id, Enums.CategoryStatus.ACTIVE);
        if (!optionalCategory.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return ResponseEntity.ok(optionalCategory.get());
    }
}
