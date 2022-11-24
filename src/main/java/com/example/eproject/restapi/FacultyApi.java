package com.example.eproject.restapi;

import com.example.eproject.dto.EventsDto;
import com.example.eproject.dto.FacultyDto;
import com.example.eproject.entity.Events;
import com.example.eproject.entity.Faculty;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/faculty")
public class FacultyApi {
    final FacultyService facultyService;
    final MessageResourceService messageResourceService;

    @GetMapping()
    public Page<FacultyDto> getList(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "status", required = false, defaultValue = "") Enums.FacultyStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status == Enums.FacultyStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("faculty.not.found"));
        } else if (status == Enums.FacultyStatus.DEACTIVE) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("faculty.not.found"));
        }
        return facultyService.findAllByStatus(Enums.FacultyStatus.ACTIVE, pageable).map(FacultyDto::new);
    }

    @GetMapping("/{id}")
    public FacultyDto getDetail(@RequestParam(value = "id", required = false, defaultValue = "1") long id,
                                @RequestParam(value = "status", required = false, defaultValue = "") Enums.FacultyStatus status) {
        if (status == Enums.FacultyStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("faculty.not.found"));
        }
        if (status == Enums.FacultyStatus.DEACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("faculty.not.found"));
        }
        Optional<Faculty> optionalFaculty = facultyService.findByIdAndStatus(id, status);
        if (!optionalFaculty.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new FacultyDto(optionalFaculty.get());
    }
}
