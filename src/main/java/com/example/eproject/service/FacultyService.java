package com.example.eproject.service;

import com.example.eproject.dto.FacultyDto;
import com.example.eproject.entity.Faculty;
import com.example.eproject.repository.FacultyRepository;
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
public class FacultyService {
    final FacultyRepository facultyRepository;
    final MessageResourceService messageResourceService;

    public Page<Faculty> findAll(Pageable pageable) {
        return facultyRepository.findAll(pageable);
    }

    public Page<Faculty> findAllByStatus(Enums.FacultyStatus status, Pageable pageable) {
        return facultyRepository.findAllByStatus(status, pageable);
    }

    public Optional<Faculty> findById(long id) {
        return facultyRepository.findById(id);
    }

    public Optional<Faculty> findByIdAndStatus(long id, Enums.FacultyStatus status) {
        return facultyRepository.findByIdAndStatus(id, status);
    }

    public Faculty create(FacultyDto facultyDto, long adminID) {
        Faculty faculty = new Faculty(facultyDto);
        BeanUtils.copyProperties(facultyDto, adminID);
        faculty.setCreatedAt(LocalDateTime.now());
        faculty.setCreatedBy(adminID);
        return facultyRepository.save(faculty);
    }

    public void delete(Faculty faculty, long adminID) {
        faculty.setStatus(Enums.FacultyStatus.DELETED);
        faculty.setDeletedAt(LocalDateTime.now());
        faculty.setDeletedBy(adminID);
        facultyRepository.save(faculty);
    }

    public Faculty update(FacultyDto facultyDto, long adminID) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyDto.getId());
        if (!optionalFaculty.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("faculty.not.found"));
        }
        Faculty faculty = optionalFaculty.get();

        BeanUtils.copyProperties(facultyDto, faculty);

        faculty.setUpdatedAt(LocalDateTime.now());
        faculty.setUpdatedBy(adminID);
        return facultyRepository.save(faculty);
    }
}
