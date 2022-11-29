package com.example.eproject.service;

import com.example.eproject.dto.MarkReportDto;
import com.example.eproject.entity.MarkReport;
import com.example.eproject.repository.MarkReportRepository;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDateTime;
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

    public MarkReport save(MarkReportDto markReportDto, long adminId) {
        MarkReport markReport = new MarkReport(markReportDto);

        BeanUtils.copyProperties(markReportDto, markReport);

        markReport.setCreatedAt(LocalDateTime.now());
        markReport.setCreatedBy(adminId);
        return markReportRepository.save(markReport);
    }

    public void delete(MarkReport markReport, long id) {
        markReport.setStatus(Enums.MarkReportStatus.DELETED);
        markReport.setDeletedAt(LocalDateTime.now());
        markReport.setDeletedBy(id);
        markReportRepository.save(markReport);
    }

    public MarkReport update(MarkReportDto markReportDto, long id) {
        Optional<MarkReport> optionalMarkReport = markReportRepository.findById(markReportDto.getId());
        if (!optionalMarkReport.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("mark.report.not.found"));
        } else {
            MarkReport markReport = optionalMarkReport.get();

            BeanUtils.copyProperties(markReportDto, markReport);

            markReport.setUpdatedAt(LocalDateTime.now());
            markReport.setUpdatedBy(id);
            return markReportRepository.save(markReport);
        }
    }
}
