package com.example.eproject.repository;

import com.example.eproject.entity.Category;
import com.example.eproject.util.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(Enums.CategoryType name);
}
