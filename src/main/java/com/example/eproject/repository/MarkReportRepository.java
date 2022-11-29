package com.example.eproject.repository;

import com.example.eproject.entity.MarkReport;
import com.example.eproject.util.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarkReportRepository extends JpaRepository<MarkReport, Long> {
    Page<MarkReport> findAll(Pageable pageable);

    Page<MarkReport> findAllByStatus(Enums.MarkReportStatus status, Pageable pageable);

    Page<MarkReport> findById(Long id, Pageable pageable);

    Optional<MarkReport> findByIdAndStatus(Long id, Enums.MarkReportStatus status);

    Page<MarkReport> findAllByAspect(Enums.PointStatus pointStatus, Pageable pageable);

    Page<MarkReport> findAllByAspectAndStatus(Enums.PointStatus pointStatus, Enums.MarkReportStatus status, Pageable pageable);
}
