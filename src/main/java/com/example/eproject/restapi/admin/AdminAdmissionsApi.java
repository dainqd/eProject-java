package com.example.eproject.restapi.admin;

import com.example.eproject.dto.AdmissionsDto;
import com.example.eproject.entity.Admissions;
import com.example.eproject.service.AdmissionsService;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("admin/api/admission")
public class AdminAdmissionsApi {
    final AdmissionsService admissionsService;
    final MessageResourceService messageResourceService;

    @GetMapping
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "status", required = false, defaultValue = "") Enums.AdmissionsStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null){
            return ResponseEntity.ok(admissionsService.findAllByStatus(status, pageable).map(AdmissionsDto::new));
        }
        return ResponseEntity.ok(admissionsService.findAll(pageable).map(AdmissionsDto::new));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id) {
        Optional<Admissions> optionalAdmissions = admissionsService.findById(id);
        if (!optionalAdmissions.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageResourceService.getMessage("id.notfound"));
        }
        return ResponseEntity.ok(new AdmissionsDto(optionalAdmissions.get()));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AdmissionsDto recruit, Authentication principal) {
        long adminId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(new AdmissionsDto(admissionsService.save(recruit, adminId)));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody AdmissionsDto request) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        long adminId = Long.parseLong(principal.getName());
        admissionsService.update(request, adminId);
        return new ResponseEntity<>(messageResourceService.getMessage("update.success"), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        long adminId = Long.parseLong(principal.getName());
        Optional<Admissions> optionalAdmissions = admissionsService.findById(id);
        if (!optionalAdmissions.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageResourceService.getMessage("id.notfound"));
        }
        admissionsService.delete(optionalAdmissions.get(), adminId);
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }
}
