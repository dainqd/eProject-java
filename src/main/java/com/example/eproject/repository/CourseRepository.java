package com.example.eproject.repository;

import com.example.eproject.entity.Course;
import com.example.eproject.util.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findById(long id, Pageable pageable);

    Page<Course> findAllByStatus(Enums.CourseStatus status, Pageable pageable);

    Optional<Course> findByIdAndStatus(long id, Enums.CourseStatus status);

    Page<Course> findAll(Pageable pageable);
}
