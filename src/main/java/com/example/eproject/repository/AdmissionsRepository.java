package com.example.eproject.repository;

import com.example.eproject.entity.Admissions;
import com.example.eproject.util.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdmissionsRepository extends JpaRepository<Admissions, Long> {
    Page<Admissions> findById(long id, Pageable pageable);

    Page<Admissions> findAllByStatus(Enums.AdmissionsStatus status, Pageable pageable);

    Optional<Admissions> findByIdAndStatus(long id, Enums.AdmissionsStatus status);

    Page<Admissions> findAll(Pageable pageable);
}
