package com.example.eproject.restapi;

import com.example.eproject.dto.CourseDto;
import com.example.eproject.entity.Course;
import com.example.eproject.service.CourseService;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.service.UserDetailsServiceImpl;
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
@RequestMapping("api/v1/course")
public class CourseApi {
    final CourseService courseService;
    final UserDetailsServiceImpl userDetailsService;
    final MessageResourceService messageResourceService;

    @GetMapping()
    public Page<CourseDto> getList(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "status", required = false, defaultValue = "") Enums.CourseStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status == Enums.CourseStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("course.not.found"));
        } else if (status != null){
            return courseService.findAllByStatusNoDelete(status, pageable).map(CourseDto::new);
        }
        return courseService.findAllByStatusNoDelete(Enums.CourseStatus.ACTIVE, pageable).map(CourseDto::new);
    }

    @GetMapping("/{id}")
    public CourseDto getDetail(@RequestParam(value = "id", required = false, defaultValue = "1") long id,
                               @RequestParam(value = "status", required = false, defaultValue = "") Enums.CourseStatus status){
        if (status == Enums.CourseStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("course.not.found"));
        }
        Optional<Course> optionalCourse = courseService.findByIdAndStatus(id, status);
        if (!optionalCourse.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new CourseDto(optionalCourse.get());
    }
}
