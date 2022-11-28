package com.example.eproject.restapi.admin;

import com.example.eproject.dto.ClassroomDto;
import com.example.eproject.entity.Classroom;
import com.example.eproject.entity.User;
import com.example.eproject.service.ClassroomService;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("admin/api/classroom")
public class AdminClassroomApi {
    final ClassroomService classroomService;
    final UserDetailsServiceImpl userDetailsService;
    final MessageResourceService messageResourceService;

    @GetMapping()
    public Page<ClassroomDto> getList(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "status", required = false, defaultValue = "") Enums.ClassroomStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            return classroomService.findAllByStatus(status, pageable).map(ClassroomDto::new);
        }
        return classroomService.findAll(pageable).map(ClassroomDto::new);
    }

    @GetMapping("{id}")
    public ClassroomDto getDetail(@PathVariable("id") Long id) {
        Optional<Classroom> optionalClassroom = classroomService.findById(id);
        if (!optionalClassroom.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new ClassroomDto(optionalClassroom.get());
    }

    @PostMapping()
    public ClassroomDto create(@RequestBody ClassroomDto classroomDto, Authentication principal) {
        String adminID = principal.getName();
        Optional<User> optionalUser = userDetailsService.findByUsername(adminID);
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        User user = optionalUser.get();
        return new ClassroomDto(classroomService.create(classroomDto, user.getId()));
    }

    @PutMapping()
    public String update(@RequestBody ClassroomDto classroomDto, Authentication principal) {
        String adminID = principal.getName();
        Optional<User> optionalUser = userDetailsService.findByUsername(adminID);
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        User user = optionalUser.get();
        Classroom classroom = new Classroom(classroomDto);
        classroomService.update(classroomDto, user.getId());
        return messageResourceService.getMessage("update.success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String adminID = principal.getName();
        Optional<User> optionalUser = userDetailsService.findByUsername(adminID);
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        User user = optionalUser.get();
        Optional<Classroom> optionalClassroom = classroomService.findById(id);
        if (!optionalClassroom.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        classroomService.delete(optionalClassroom.get(), user.getId());
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }
}
