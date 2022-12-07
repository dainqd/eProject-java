package com.example.eproject.repository;

import com.example.eproject.entity.Manager;
import com.example.eproject.util.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Page<Manager> findAll(Pageable pageable);

    Page<Manager> findAllByStatus(Enums.ManagerStatus status, Pageable pageable);

    Optional<Manager> findById(Long id);

    Optional<Manager> findByIdAndStatus(Long id, Enums.ManagerStatus status);

    Optional<Manager> findByEmail(String email);
}
