package com.example.eproject.service;

import com.example.eproject.dto.ClassroomDto;
import com.example.eproject.entity.Classroom;
import com.example.eproject.repository.ClassroomRepository;
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
public class ClassroomService {
    final MessageResourceService messageResourceService;
    final ClassroomRepository classroomRepository;

    public Page<Classroom> findAll(Pageable pageable) {
        return classroomRepository.findAll(pageable);
    }

    public Page<Classroom> findAllByStatus(Enums.ClassroomStatus status, Pageable pageable) {
        return classroomRepository.findAllByStatus(status, pageable);
    }

    public Optional<Classroom> findByIdAndStatus(long id, Enums.ClassroomStatus status) {
        if (status != Enums.ClassroomStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("classroom.not.found"));
        }
        return classroomRepository.findByIdAndStatus(id, status);
    }

    public Optional<Classroom> findById(long id) {
        return classroomRepository.findById(id);
    }

    public Classroom create(ClassroomDto classroomDto, long adminID) {
        Classroom classroom = new Classroom();
        BeanUtils.copyProperties(classroomDto, classroom);
        classroom.setStartDate(Date.valueOf(classroomDto.getStartDate()));
        classroom.setEndDate(Date.valueOf(classroomDto.getEndDate()));
        classroom.setCreatedAt(LocalDateTime.now());
        classroom.setCreatedBy(adminID);
        return classroomRepository.save(classroom);
    }

    public void delete(Classroom classroom, long adminID) {
        classroom.setStatus(Enums.ClassroomStatus.DELETED);
        classroom.setDeletedAt(LocalDateTime.now());
        classroom.setDeletedBy(adminID);
    }

    public Classroom update(ClassroomDto classroomDto, long adminID) {
        Optional<Classroom> optionalClassroom = classroomRepository.findById(classroomDto.getId());
        if (!optionalClassroom.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("id.not.found"));
        }
        Classroom classroom = new Classroom();
        BeanUtils.copyProperties(classroomDto, classroom);
        classroom.setStartDate(Date.valueOf(classroomDto.getStartDate()));
        classroom.setEndDate(Date.valueOf(classroomDto.getEndDate()));
        classroom.setUpdatedAt(LocalDateTime.now());
        classroom.setUpdatedBy(adminID);
        return classroomRepository.save(classroom);
    }
}
