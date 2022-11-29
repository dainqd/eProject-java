package com.example.eproject.restapi.admin;

import com.example.eproject.dto.AttendanceReportDto;
import com.example.eproject.entity.AttendanceReport;
import com.example.eproject.entity.User;
import com.example.eproject.service.AttendanceReportService;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.service.UserDetailsServiceImpl;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("admin/api/v1/attendance-report")
public class AdminAttendanceReportApi {
    final AttendanceReportService attendanceReportService;
    final UserDetailsServiceImpl userDetailsService;
    final MessageResourceService messageResourceService;

    @GetMapping()
    public Page<AttendanceReportDto> getList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                             @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                             @RequestParam(value = "status", required = false, defaultValue = "") Enums.AttendanceStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            return attendanceReportService.findAllByStatus(status, pageable).map(AttendanceReportDto::new);
        }
        return attendanceReportService.findAll(pageable).map(AttendanceReportDto::new);
    }

    @GetMapping("{id}")
    public AttendanceReportDto getDetail(@PathVariable("id") long id) {
        Optional<AttendanceReport> optionalAttendanceReport = attendanceReportService.findById(id);
        if (!optionalAttendanceReport.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new AttendanceReportDto(optionalAttendanceReport.get());
    }

    @PostMapping()
    public AttendanceReportDto create(@RequestBody AttendanceReportDto attendanceReportDto, Authentication principal) {
        String admin = principal.getName();
        Optional<User> optionalUser = userDetailsService.findByUsername(admin);
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        User user = optionalUser.get();
        return new AttendanceReportDto(attendanceReportService.create(attendanceReportDto, user.getId()));
    }

    @PutMapping()
    public String update(@RequestBody AttendanceReportDto attendanceReportDto, Authentication principal) {
        String admin = principal.getName();
        Optional<User> optionalUser = userDetailsService.findByUsername(admin);
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        User user = optionalUser.get();
        attendanceReportService.update(attendanceReportDto, user.getId());
        return messageResourceService.getMessage("update.success");
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id, Authentication principal) {
        String admin = principal.getName();
        Optional<User> optionalUser = userDetailsService.findByUsername(admin);
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        User user = optionalUser.get();
        Optional<AttendanceReport> optionalAttendanceReport = attendanceReportService.findById(id);
        if (!optionalAttendanceReport.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        AttendanceReport attendanceReport = optionalAttendanceReport.get();
        attendanceReportService.delete(attendanceReport, user.getId());
        return messageResourceService.getMessage("delete.success");
    }
}
