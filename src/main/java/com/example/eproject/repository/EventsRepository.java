package com.example.eproject.repository;

import com.example.eproject.entity.Events;
import com.example.eproject.util.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {
    Page<Events> findById(long id, Pageable pageable);

    Page<Events> findAllByStatus(Enums.EventsStatus status, Pageable pageable);

    Optional<Events> findByIdAndStatus(long id, Enums.EventsStatus status);

    Page<Events> findAll(Pageable pageable);
}
