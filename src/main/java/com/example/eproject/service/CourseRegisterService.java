package com.example.eproject.service;

import com.example.eproject.dto.CourseRegisterDto;
import com.example.eproject.entity.CourseRegister;
import com.example.eproject.repository.CourseRegisterRepository;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseRegisterService {
    final CourseRegisterRepository courseRegisterRepository;

    public Page<CourseRegister> findAll(Pageable pageable) {
        return courseRegisterRepository.findAll(pageable);
    }

    public Page<CourseRegister> findAllByStatus(Enums.CourseRegisterStatus status, Pageable pageable) {
        return courseRegisterRepository.findAllByStatus(status, pageable);
    }

    public Optional<CourseRegister> findById(long id) {
        return courseRegisterRepository.findById(id);
    }

    public Optional<CourseRegister> findByIdAndStatus(long id, Enums.CourseRegisterStatus status) {
        return courseRegisterRepository.findByIdAndStatus(id, status);
    }

    public CourseRegister create(CourseRegisterDto courseRegisterDto, long adminID) {
        CourseRegister courseRegister = new CourseRegister();
        BeanUtils.copyProperties(courseRegisterDto, courseRegister);
        courseRegister.setCreatedAt(LocalDateTime.now());
        courseRegister.setCreatedBy(adminID);
        return courseRegisterRepository.save(courseRegister);
    }

    public CourseRegister save(CourseRegisterDto cvRecruitmentDto) {
        CourseRegister courseRegister = new CourseRegister(cvRecruitmentDto);
        return courseRegisterRepository.save(courseRegister);
    }

    public CourseRegister update(CourseRegisterDto courseRegisterDto, long adminID) {
        CourseRegister courseRegister = new CourseRegister();
        BeanUtils.copyProperties(courseRegisterDto, courseRegister);
        courseRegister.setUpdatedAt(LocalDateTime.now());
        courseRegister.setUpdatedBy(adminID);
        return courseRegisterRepository.save(courseRegister);
    }

    public void delete(CourseRegister courseRegister, long adminID) {
        courseRegister.setStatus(Enums.CourseRegisterStatus.DELETED);
        courseRegister.setDeletedAt(LocalDateTime.now());
        courseRegister.setDeletedBy(adminID);
    }
}
