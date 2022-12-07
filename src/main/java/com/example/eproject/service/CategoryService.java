package com.example.eproject.service;

import com.example.eproject.entity.Category;
import com.example.eproject.repository.CategoryRepository;
import com.example.eproject.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Page<Category> findAllByStatus(Enums.CategoryStatus status, Pageable pageable) {
        return categoryRepository.findByStatus(status, pageable);
    }

    public Optional<Category> findByIdAndStatus(long id, Enums.CategoryStatus status) {
        return categoryRepository.findByIdAndStatus(id, status);
    }

    public Category save(Category category, long adminID) {
        category.setCreatedAt(LocalDateTime.now());
        category.setCreatedBy(adminID);
        return categoryRepository.save(category);
    }

    public Category update(Category category, long adminID) {
        Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
        if (!optionalCategory.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR");
        }
        Category category1 = optionalCategory.get();
        category1.setName(category.getName());
        category1.setStatus(category.getStatus());
        category1.setCreatedAt(LocalDateTime.now());
        category1.setCreatedBy(adminID);
        return categoryRepository.save(category1);
    }

    public void delete(Category category1, long adminID) {
        Optional<Category> optionalCategory = categoryRepository.findById(category1.getId());
        if (!optionalCategory.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR");
        }
        Category category = optionalCategory.get();
        category.setStatus(Enums.CategoryStatus.DELETED);
        category.setDeletedAt(LocalDateTime.now());
        category.setDeletedBy(adminID);
        categoryRepository.save(category);
    }
}
