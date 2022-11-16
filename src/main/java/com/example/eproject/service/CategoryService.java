package com.example.eproject.service;

import com.example.eproject.entity.Category;
import com.example.eproject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteById(long id) {
        categoryRepository.deleteById(id);
    }

//    public List<Category> findAllByStatus(Boolean status){
//        return categoryRepository.findAllByStatus(status);
//    }

//    public Optional<Category> getListByIdAndStatus(long id, boolean status){
//        return categoryRepository.findAllByIdAndStatus(id, status);
//    }
}
