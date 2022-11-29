package com.example.eproject.restapi;

import com.example.eproject.dto.AttendanceReportDto;
import com.example.eproject.entity.AttendanceReport;
import com.example.eproject.service.AttendanceReportService;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/attendance-report")
public class AttendanceReportApi {
    final AttendanceReportService attendanceReportService;
    final MessageResourceService messageResourceService;

    @GetMapping()
    public Page<AttendanceReportDto> getList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                             @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                             @RequestParam(value = "status", required = false, defaultValue = "") Enums.AttendanceStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            if (status == Enums.AttendanceStatus.DELETED || status == Enums.AttendanceStatus.BLOCK) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        messageResourceService.getMessage("attendance.report.not.found"));
            } else {
                return attendanceReportService.findAllByStatus(status, pageable).map(AttendanceReportDto::new);
            }
        }
        return attendanceReportService.findAllByStatus(Enums.AttendanceStatus.ACTIVE, pageable).map(AttendanceReportDto::new);
    }

    @GetMapping("{id}")
    public AttendanceReportDto getDetail(@RequestParam(value = "id", required = false, defaultValue = "1") long id,
                                         @RequestParam(value = "status", required = false, defaultValue = "") Enums.AttendanceStatus status) {
        if (status != null) {
            if (status == Enums.AttendanceStatus.DELETED || status == Enums.AttendanceStatus.BLOCK) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        messageResourceService.getMessage("attendance.report.not.found"));
            }
        }
        Optional<AttendanceReport> optionalAttendanceReport = attendanceReportService.findById(id);
        if (!optionalAttendanceReport.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new AttendanceReportDto(optionalAttendanceReport.get());
    }
}
