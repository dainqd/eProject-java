package com.example.eproject.service;

import com.example.eproject.dto.AdmissionsDto;
import com.example.eproject.entity.Admissions;
import com.example.eproject.repository.AdmissionsRepository;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdmissionsService {
    @Autowired
    final AdmissionsRepository admissionsRepository;
    final MessageResourceService messageResourceService;

    public Page<Admissions> findAll(Pageable pageable) {
        return admissionsRepository.findAll(pageable);
    }

    public Page<Admissions> findAllByStatus(Enums.AdmissionsStatus status, Pageable pageable) {
        return admissionsRepository.findAllByStatus(status, pageable);
    }

    public Optional<Admissions> findById(long id) {
        return admissionsRepository.findById(id);
    }

    public Admissions save(AdmissionsDto admissionsDto, long adminId) {
        Admissions admissions = new Admissions(admissionsDto);

        BeanUtils.copyProperties(admissionsDto, admissions);

        admissions.setBirthday(Date.valueOf(admissionsDto.getBirthday()));
        admissions.setCreatedAt(LocalDateTime.now());
        admissions.setCreatedBy(adminId);
        return admissionsRepository.save(admissions);
    }

    public Admissions create(AdmissionsDto admissionsDto) {
        Admissions admissions = new Admissions(admissionsDto);

        BeanUtils.copyProperties(admissionsDto, admissions);

        admissions.setBirthday(Date.valueOf(admissionsDto.getBirthday()));
        admissions.setCreatedAt(LocalDateTime.now());
        return admissionsRepository.save(admissions);
    }

    public void delete(Admissions admissions, long id) {
        admissions.setStatus(Enums.AdmissionsStatus.DELETED);
        admissions.setDeletedAt(LocalDateTime.now());
        admissions.setDeletedBy(id);
        admissionsRepository.save(admissions);
    }

    public Admissions update(AdmissionsDto admissionsDto, long id) {
        Optional<Admissions> optionalAdmissions = admissionsRepository.findById(admissionsDto.getId());
        if (!optionalAdmissions.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("admissions.not.found"));
        } else {
            Admissions admissions = optionalAdmissions.get();

            BeanUtils.copyProperties(admissionsDto, admissions);

            admissions.setBirthday(Date.valueOf(admissionsDto.getBirthday()));
            admissions.setUpdatedAt(LocalDateTime.now());
            admissions.setUpdatedBy(id);
            return admissionsRepository.save(admissions);
        }
    }
}
