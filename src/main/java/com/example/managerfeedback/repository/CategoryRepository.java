package com.example.managerfeedback.repository;

import com.example.managerfeedback.entity.Category;
import com.example.managerfeedback.util.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    List<Category> findAllByStatus(boolean status);

//    Optional<Category> findAllByIdAndStatus(Integer id, boolean status);

    Optional<Category> findByName(Enums.CategoryType name);
}
