package com.example.eproject.repository;

import com.example.eproject.entity.AttendanceReport;
import com.example.eproject.util.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendanceReportRepository extends JpaRepository<AttendanceReport, Long> {
    Page<AttendanceReport> findAll(Pageable pageable);

    Page<AttendanceReport> findAllByStatus(Enums.AttendanceStatus status, Pageable pageable);

    Optional<AttendanceReport> findByIdAndStatus(Long id, Enums.AttendanceStatus status);
}
