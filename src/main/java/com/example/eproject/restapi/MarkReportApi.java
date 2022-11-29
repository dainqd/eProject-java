package com.example.eproject.restapi;

import com.example.eproject.dto.MarkReportDto;
import com.example.eproject.entity.MarkReport;
import com.example.eproject.service.MarkReportService;
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
@RequestMapping("api/v1/mark-report")
public class MarkReportApi {
    final MarkReportService markReportService;
    final MessageResourceService messageResourceService;

    @GetMapping()
    public Page<MarkReportDto> getListByStatus(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                               @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                               @RequestParam(value = "status", required = false, defaultValue = "") Enums.MarkReportStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            if (status == Enums.MarkReportStatus.DELETED || status == Enums.MarkReportStatus.BLOCK) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        messageResourceService.getMessage("mark.report.not.found"));
            } else {
                return markReportService.findAllByStatus(status, pageable).map(MarkReportDto::new);
            }
        }
        return markReportService.findAllByStatus(Enums.MarkReportStatus.ACTIVE, pageable).map(MarkReportDto::new);
    }

    @GetMapping("/{id}")
    public MarkReportDto getDetailByStatus(@RequestParam(value = "id", required = false, defaultValue = "1") long id,
                                           @RequestParam(value = "status", required = false, defaultValue = "") Enums.MarkReportStatus status) {
        if (status != null) {
            if (status == Enums.MarkReportStatus.DELETED || status == Enums.MarkReportStatus.BLOCK) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        messageResourceService.getMessage("mark.report.not.found"));
            }
        }
        Optional<MarkReport> markReportOptional = markReportService.findByIdAndStatus(id, status);
        if (!markReportOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new MarkReportDto(markReportOptional.get());
    }
}
