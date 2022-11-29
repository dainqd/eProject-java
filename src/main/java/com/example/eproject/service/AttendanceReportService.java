package com.example.eproject.service;

import com.example.eproject.dto.AttendanceReportDto;
import com.example.eproject.entity.AttendanceReport;
import com.example.eproject.repository.AttendanceReportRepository;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceReportService {
    final AttendanceReportRepository attendanceReportRepository;
    final MessageResourceService messageResourceService;

    public Page<AttendanceReport> findAll(Pageable pageable) {
        return attendanceReportRepository.findAll(pageable);
    }

    public Page<AttendanceReport> findAllByStatus(Enums.AttendanceStatus status, Pageable pageable) {
        return attendanceReportRepository.findAllByStatus(status, pageable);
    }

    public Optional<AttendanceReport> findById(long id) {
        return attendanceReportRepository.findById(id);
    }

    public Optional<AttendanceReport> findByIdAndStatus(long id, Enums.AttendanceStatus status) {
        return attendanceReportRepository.findByIdAndStatus(id, status);
    }

    public AttendanceReport create(AttendanceReportDto attendanceReportDto, long adminID) {
        AttendanceReport attendanceReport = new AttendanceReport();

        BeanUtils.copyProperties(attendanceReportDto, attendanceReport);

        attendanceReport.setCreatedAt(LocalDateTime.now());
        attendanceReport.setCreatedBy(adminID);

        return attendanceReportRepository.save(attendanceReport);
    }

    public void delete(AttendanceReport attendanceReport, long adminID) {
        attendanceReport.setStatus(Enums.AttendanceStatus.DELETED);
        attendanceReport.setDeletedAt(LocalDateTime.now());
        attendanceReport.setDeletedBy(adminID);
        attendanceReportRepository.save(attendanceReport);
    }

    public AttendanceReport update(AttendanceReportDto attendanceReportDto, long adminID) {
        Optional<AttendanceReport> optionalAttendanceReport = attendanceReportRepository.findById(attendanceReportDto.getId());
        if (!optionalAttendanceReport.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("admissions.not.found"));
        }
        AttendanceReport attendanceReport = optionalAttendanceReport.get();

        BeanUtils.copyProperties(attendanceReportDto, attendanceReport);

        attendanceReport.setUpdatedAt(LocalDateTime.now());
        attendanceReport.setUpdatedBy(adminID);

        return attendanceReportRepository.save(attendanceReport);
    }
}
