package com.example.eproject.service;

import com.example.eproject.entity.MarkReport;
import com.example.eproject.repository.MarkReportRepository;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarkReportService {
    final MarkReportRepository markReportRepository;
    final MessageResourceService messageResourceService;

    public Page<MarkReport> findAll(Pageable pageable) {
        return markReportRepository.findAll(pageable);
    }

    public Page<MarkReport> findAllByStatus(Enums.MarkReportStatus status, Pageable pageable) {
        return markReportRepository.findAllByStatus(status, pageable);
    }

    public Page<MarkReport> findAllByPoint(Enums.PointStatus pointStatus, Pageable pageable) {
        return markReportRepository.findAllByAspect(pointStatus, pageable);
    }

    public Page<MarkReport> findAllByPointAndStatus(Enums.PointStatus pointStatus, Enums.MarkReportStatus status, Pageable pageable) {
        return markReportRepository.findAllByAspectAndStatus(pointStatus, status, pageable);
    }

    public Optional<MarkReport> findById(long id) {
        return markReportRepository.findById(id);
    }

    public Optional<MarkReport> findByIdAndStatus(long id, Enums.MarkReportStatus status) {
        if (status != null) {
            if (status == Enums.MarkReportStatus.DELETED || status == Enums.MarkReportStatus.BLOCK) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("mark.report.not.found"));
            } else {
                return markReportRepository.findByIdAndStatus(id, status);
            }
        }
        return markReportRepository.findByIdAndStatus(id, Enums.MarkReportStatus.ACTIVE);
    }
}
