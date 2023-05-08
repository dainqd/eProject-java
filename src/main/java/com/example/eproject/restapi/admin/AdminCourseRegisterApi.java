package com.example.eproject.restapi.admin;

import com.example.eproject.dto.CourseRegisterDto;
import com.example.eproject.entity.CourseRegister;
import com.example.eproject.entity.User;
import com.example.eproject.service.CourseRegisterService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/api/course/reigster")
public class AdminCourseRegisterApi {
    final CourseRegisterService courseRegisterService;
    final MessageResourceService messageResourceService;
    final UserDetailsServiceImpl userDetailsService;

    @GetMapping()
    public Page<CourseRegisterDto> getList(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "status", required = false, defaultValue = "") Enums.CourseRegisterStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            return courseRegisterService.findAllByStatus(status, pageable).map(CourseRegisterDto::new);
        }
        return courseRegisterService.findAll(pageable).map(CourseRegisterDto::new);
    }

    @GetMapping("{id}")
    public CourseRegisterDto getDetail(@PathVariable("id") Long id) {
        Optional<CourseRegister> optionalCourseRegister = courseRegisterService.findById(id);
        if (!optionalCourseRegister.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new CourseRegisterDto(optionalCourseRegister.get());
    }

    @PostMapping()
    public CourseRegisterDto create(@RequestBody CourseRegisterDto courseRegisterDto, Authentication principal) {
        String adminId = principal.getName();
        Optional<User> op = userDetailsService.findByUsername(adminId);
        if (!op.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = op.get();
        return new CourseRegisterDto(courseRegisterService.create(courseRegisterDto, user.getId()));
    }

    @PutMapping()
    public String update(@RequestBody CourseRegisterDto request, Authentication principal) {
        String adminId = principal.getName();
        Optional<User> op = userDetailsService.findByUsername(adminId);
        if (!op.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = op.get();
        courseRegisterService.update(request, user.getId());
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
        Optional<CourseRegister> optionalCourseRegister = courseRegisterService.findById(id);
        if (!optionalCourseRegister.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        courseRegisterService.delete(optionalCourseRegister.get(), user.getId());
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }
}
