package com.example.eproject.service;

import com.example.eproject.dto.CourseDto;
import com.example.eproject.entity.Course;
import com.example.eproject.repository.CourseRepository;
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
public class CourseService {
    final CourseRepository courseRepository;
    final MessageResourceService messageResourceService;

    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    public Page<Course> findAllByStatus(Enums.CourseStatus status, Pageable pageable) {
        return courseRepository.findAllByStatus(status, pageable);
    }

    public Optional<Course> findById(long id) {
        return courseRepository.findById(id);
    }

    public Optional<Course> findByIdAndStatus(long id, Enums.CourseStatus status) {
        return courseRepository.findByIdAndStatus(id, status);
    }

    public Course create(CourseDto courseDto, long adminID) {
        Course course = new Course();

        BeanUtils.copyProperties(courseDto, course);

        course.setStartDate(Date.valueOf(courseDto.getStartDate()));
        course.setEndDate(Date.valueOf(courseDto.getEndDate()));

        System.out.println(course.getStartDate() + " " + course.getEndDate());
        course.setCreatedAt(LocalDateTime.now());
        course.setCreatedBy(adminID);
        return courseRepository.save(course);
    }

    public void delete(Course course, long adminID){
        course.setStatus(Enums.CourseStatus.DELETED);
        course.setDeletedAt(LocalDateTime.now());
        course.setDeletedBy(adminID);
        courseRepository.save(course);
    }

    public Course update(CourseDto courseDto, long adminID){
        Optional<Course> optionalCourse = courseRepository.findById(courseDto.getId());
        if (!optionalCourse.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("course.notfound"));
        }
        Course course = optionalCourse.get();

        BeanUtils.copyProperties(courseDto, course);

        course.setStartDate(Date.valueOf(courseDto.getStartDate()));
        course.setEndDate(Date.valueOf(courseDto.getEndDate()));

        course.setUpdatedAt(LocalDateTime.now());
        course.setUpdatedBy(adminID);
        return courseRepository.save(course);
    }
}
