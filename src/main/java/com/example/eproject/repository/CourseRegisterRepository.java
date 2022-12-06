package com.example.eproject.repository;

import com.example.eproject.entity.CourseRegister;
import com.example.eproject.util.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRegisterRepository extends JpaRepository<CourseRegister, Long> {
    Page<CourseRegister> findAll(Pageable pageable);

    Page<CourseRegister> findAllByStatus(Enums.CourseRegisterStatus status, Pageable pageable);

    Optional<CourseRegister> findById(long id);

    Optional<CourseRegister> findByIdAndStatus(long id, Enums.CourseRegisterStatus status);
}
