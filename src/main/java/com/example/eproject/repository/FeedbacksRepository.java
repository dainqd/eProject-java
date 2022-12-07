package com.example.eproject.repository;

import com.example.eproject.entity.Feedbacks;
import com.example.eproject.util.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbacksRepository extends JpaRepository<Feedbacks, Long> {
    Page<Feedbacks> findAll(Pageable pageable);
    Page<Feedbacks> findAllByStatus(Enums.FeedbackStatus status, Pageable pageable);
    Optional<Feedbacks> findByIdAndStatus(long id, Enums.FeedbackStatus status);
    Optional<Feedbacks> findById(long id);
}
