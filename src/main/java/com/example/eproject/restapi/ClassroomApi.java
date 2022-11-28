package com.example.eproject.restapi;

import com.example.eproject.dto.ClassroomDto;
import com.example.eproject.entity.Classroom;
import com.example.eproject.service.ClassroomService;
import com.example.eproject.service.MessageResourceService;
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
@RequestMapping("api/v1/classroom")
public class ClassroomApi {
    final ClassroomService classroomService;
    final MessageResourceService messageResourceService;

    @GetMapping()
    public Page<ClassroomDto> getPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "status", required = false, defaultValue = "") Enums.ClassroomStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            if (status != Enums.ClassroomStatus.ACTIVE){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("classroom.not.found"));
            }
        }
        return classroomService.findAllByStatus(Enums.ClassroomStatus.ACTIVE, pageable).map(ClassroomDto::new);
    }

    @GetMapping("/{id}")
    public ClassroomDto getDetail(@RequestParam(value = "id", required = false, defaultValue = "1") long id,
                               @RequestParam(value = "status", required = false, defaultValue = "") Enums.ClassroomStatus status){
        if (status == Enums.ClassroomStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("classroom.not.found"));
        }
        Optional<Classroom> optionalClassroom = classroomService.findByIdAndStatus(id, status);
        if (!optionalClassroom.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new ClassroomDto(optionalClassroom.get());
    }
}
