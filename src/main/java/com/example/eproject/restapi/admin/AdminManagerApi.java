package com.example.eproject.restapi.admin;

import com.example.eproject.dto.ManagerDto;
import com.example.eproject.entity.Manager;
import com.example.eproject.entity.User;
import com.example.eproject.service.ManagerService;
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
@RequestMapping("admin/api/manager")
public class AdminManagerApi {
    final ManagerService managerService;
    final MessageResourceService messageResourceService;
    final UserDetailsServiceImpl userDetailsService;

    @GetMapping()
    public Page<ManagerDto> getList(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "status", required = false, defaultValue = "") Enums.ManagerStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            return managerService.findAllByStatus(status, pageable).map(ManagerDto::new);
        }
        return managerService.findAll(pageable).map(ManagerDto::new);
    }

    @GetMapping("{id}")
    public ManagerDto getDetail(@PathVariable("id") Long id) {
        Optional<Manager> optionalManager = managerService.findById(id);
        if (!optionalManager.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new ManagerDto(optionalManager.get());
    }

    @PostMapping()
    public ManagerDto create(@RequestBody ManagerDto managerDto, Authentication principal) {
        String adminId = principal.getName();
        Optional<User> op = userDetailsService.findByUsername(adminId);
        if (!op.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = op.get();
        System.out.println(adminId);
        return new ManagerDto(managerService.create(managerDto, user.getId()));
    }

    @PutMapping()
    public String update(@RequestBody ManagerDto request, Authentication principal) {
        String adminId = principal.getName();
        Optional<User> op = userDetailsService.findByUsername(adminId);
        if (!op.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = op.get();
        System.out.println(adminId);
        managerService.update(request, user.getId());
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
        Optional<Manager> optionalManager = managerService.findById(id);
        if (!optionalManager.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        managerService.delete(optionalManager.get(), user.getId());
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }
}
