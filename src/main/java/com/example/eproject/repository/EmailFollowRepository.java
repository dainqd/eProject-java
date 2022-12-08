package com.example.eproject.repository;

import com.example.eproject.entity.EmailFollow;
import com.example.eproject.util.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailFollowRepository extends JpaRepository<EmailFollow, Long> {
    Page<EmailFollow> findAll(Pageable pageable);

    Page<EmailFollow> findAllByStatus(Enums.EmailFollowStatus status, Pageable pageable);

    Optional<EmailFollow> findById(long id);

    Optional<EmailFollow> findByIdAndStatus(long id, Enums.EmailFollowStatus status);
}
