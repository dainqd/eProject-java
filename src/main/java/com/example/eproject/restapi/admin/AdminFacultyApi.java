package com.example.eproject.restapi.admin;

import com.example.eproject.dto.FacultyDto;
import com.example.eproject.entity.Faculty;
import com.example.eproject.entity.User;
import com.example.eproject.service.FacultyService;
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
@RequestMapping("admin/api/v1/faculty")
public class AdminFacultyApi {
    final FacultyService facultyService;
    final UserDetailsServiceImpl userDetailsService;
    final MessageResourceService messageResourceService;

    @GetMapping()
    public Page<FacultyDto> getList(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "status", required = false, defaultValue = "") Enums.FacultyStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            return facultyService.findAllByStatus(status, pageable).map(FacultyDto::new);
        }
        return facultyService.findAll(pageable).map(FacultyDto::new);
    }

    @GetMapping("/{id}")
    public FacultyDto getDetail(@PathVariable("id") long id) {
        Optional<Faculty> optionalFaculty = facultyService.findById(id);
        if (!optionalFaculty.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new FacultyDto(optionalFaculty.get());
    }

    @PostMapping()
    public FacultyDto create(@RequestBody FacultyDto facultyDto, Authentication principal) {
        String adminID = principal.getName();
        Optional<User> optionalUse = userDetailsService.findByUsername(adminID);
        if (!optionalUse.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = optionalUse.get();
        System.out.println(adminID);
        Faculty course = new Faculty(facultyDto);
        return new FacultyDto(facultyService.create(facultyDto, user.getId()));
    }

    @PutMapping()
    public String update(@RequestBody FacultyDto facultyDto, Authentication principal) {
        String adminId = principal.getName();
        Optional<User> op = userDetailsService.findByUsername(adminId);
        if (!op.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = op.get();
        System.out.println(adminId);
        Faculty course = new Faculty(facultyDto);
        facultyService.update(facultyDto, user.getId());
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
        System.out.println(adminId);
        Optional<Faculty> optionalFaculty = facultyService.findById(id);
        if (!optionalFaculty.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        facultyService.delete(optionalFaculty.get(), user.getId());
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }
}
