package com.example.eproject.restapi.admin;

import com.example.eproject.dto.MarkReportDto;
import com.example.eproject.dto.MarkReportDto;
import com.example.eproject.entity.MarkReport;
import com.example.eproject.entity.MarkReport;
import com.example.eproject.entity.User;
import com.example.eproject.service.MarkReportService;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.service.UserDetailsServiceImpl;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("admin/api/mark-report")
public class AdminMarkReportApi {
    final MarkReportService markReportService;
    final UserDetailsServiceImpl userDetailsService;
    final MessageResourceService messageResourceService;

    @GetMapping()
    public Page<MarkReportDto> getList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                       @RequestParam(value = "status", required = false, defaultValue = "") Enums.MarkReportStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            return markReportService.findAllByStatus(status, pageable).map(MarkReportDto::new);
        }
        return markReportService.findAll(pageable).map(MarkReportDto::new);
    }

    @GetMapping("/{id}")
    public MarkReportDto getDetail(@PathVariable("id") long id) {
        Optional<MarkReport> markReportOptional = markReportService.findById(id);
        if (!markReportOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new MarkReportDto(markReportOptional.get());
    }

    @GetMapping("/point")
    public Page<MarkReportDto> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                      @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                      @RequestParam(value = "aspect", required = false, defaultValue = "") Enums.PointStatus pointStatus) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (pointStatus != null) {
            return markReportService.findAllByPoint(pointStatus, pageable).map(MarkReportDto::new);
        }
        return markReportService.findAll(pageable).map(MarkReportDto::new);
    }

    @PostMapping()
    public MarkReportDto create(@RequestBody MarkReportDto markReportDto, Authentication principal) {
        String adminId = principal.getName();
        Optional<User> op = userDetailsService.findByUsername(adminId);
        if (!op.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = op.get();
        System.out.println(adminId);
        return new MarkReportDto(markReportService.save(markReportDto, user.getId()));
    }

    @PutMapping()
    public String update(@RequestBody MarkReportDto request, Authentication principal) {
        String adminId = principal.getName();
        Optional<User> op = userDetailsService.findByUsername(adminId);
        if (!op.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = op.get();
        System.out.println(adminId);
        MarkReport admissions = new MarkReport(request);
        markReportService.update(request, user.getId());
        return messageResourceService.getMessage("update.success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String adminId = principal.getName();
        Optional<User> op = userDetailsService.findByUsername(adminId);
        if (!op.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = op.get();
        System.out.println(adminId);
        Optional<MarkReport> optionalMarkReport = markReportService.findById(id);
        if (!optionalMarkReport.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        markReportService.delete(optionalMarkReport.get(), user.getId());
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }
}
