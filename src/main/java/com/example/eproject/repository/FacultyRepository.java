package com.example.eproject.repository;

import com.example.eproject.entity.Faculty;
import com.example.eproject.util.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Page<Faculty> findById(long id, Pageable pageable);

    Page<Faculty> findAllByStatus(Enums.FacultyStatus status, Pageable pageable);

    Optional<Faculty> findByIdAndStatus(long id, Enums.FacultyStatus status);

    Page<Faculty> findAll(Pageable pageable);
}
