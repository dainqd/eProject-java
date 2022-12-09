package com.example.eproject.repository;

import com.example.eproject.entity.Category;
import com.example.eproject.util.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAll(Pageable pageable);

    Page<Category> findByStatus(Enums.CategoryStatus status, Pageable pageable);

    List<Category> findAllByStatus(Enums.CategoryStatus status);

    Optional<Category> findByName(Enums.CategoryType name);

    Optional<Category> findById(long id);

    Optional<Category> findByIdAndStatus(long id, Enums.CategoryStatus status);
}
