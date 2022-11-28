package com.example.eproject.restapi.admin;

import com.example.eproject.entity.Category;
import com.example.eproject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("admin/api/category")
public class AdminCategoryApi {
    @Autowired
    CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<Category>> getLists() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetails(@PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryService.findById(id);
        if (!optionalCategory.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalCategory.get());
    }

    @PostMapping()
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.save(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Integer id, @RequestBody Category category) {
        Optional<Category> optionalCategory = categoryService.findById(id);
        if ((!optionalCategory.isPresent())) {
            ResponseEntity.badRequest().build();
        }
        Category existCategory = optionalCategory.get();

        existCategory.setName(category.getName());
        return ResponseEntity.ok(categoryService.save(existCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if ((!categoryService.findById(id).isPresent())) {
            ResponseEntity.badRequest().build();
        }
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
