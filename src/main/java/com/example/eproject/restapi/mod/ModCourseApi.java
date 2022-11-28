package com.example.eproject.restapi.mod;

import com.example.eproject.dto.CourseDto;
import com.example.eproject.entity.Course;
import com.example.eproject.entity.User;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("mod/api/course")
public class ModCourseApi {
    final CourseService courseService;
    final UserDetailsServiceImpl userDetailsService;
    final MessageResourceService messageResourceService;

    @GetMapping()
    public Page<CourseDto> getList(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "status", required = false, defaultValue = "") Enums.CourseStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            return courseService.findAllByStatus(status, pageable).map(CourseDto::new);
        }
        return courseService.findAll(pageable).map(CourseDto::new);
    }

    @GetMapping("/{id}")
    public CourseDto getDetail(@PathVariable("id") long id) {
        Optional<Course> optionalCourse = courseService.findById(id);
        if (!optionalCourse.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new CourseDto(optionalCourse.get());
    }

    @PostMapping()
    public CourseDto create(@RequestBody CourseDto courseDto, Authentication principal) {
        String adminID = principal.getName();
        Optional<User> optionalUse = userDetailsService.findByUsername(adminID);
        if (!optionalUse.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = optionalUse.get();
        System.out.println(adminID);
        Course course = new Course(courseDto);
        return new CourseDto(courseService.create(courseDto, user.getId()));
    }

    @PutMapping()
    public String update(@RequestBody CourseDto courseDto, Authentication principal) {
        String adminId = principal.getName();
        Optional<User> op = userDetailsService.findByUsername(adminId);
        if (!op.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = op.get();
        System.out.println(adminId);
        Course course = new Course(courseDto);
        courseService.update(courseDto, user.getId());
        return messageResourceService.getMessage("update.success");
    }
}
