package com.example.managerfeedback.repository;


import com.example.managerfeedback.entity.Feedbacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbacksRepository extends JpaRepository<Feedbacks, Long> {
    List<Feedbacks> findAllByStatus(boolean status);

    Optional<Feedbacks> findAllByIdAndStatus(long id, boolean status);
}
