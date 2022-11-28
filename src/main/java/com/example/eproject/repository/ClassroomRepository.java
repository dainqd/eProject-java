package com.example.eproject.repository;

import com.example.eproject.entity.Classroom;
import com.example.eproject.util.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Page<Classroom> findById(long id, Pageable pageable);

    Page<Classroom> findAllByStatus(Enums.ClassroomStatus status, Pageable pageable);

    Optional<Classroom> findByIdAndStatus(long id, Enums.ClassroomStatus status);

    Page<Classroom> findAll(Pageable pageable);
}
