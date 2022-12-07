package com.example.eproject.restapi.mod;

import com.example.eproject.entity.Category;
import com.example.eproject.entity.User;
import com.example.eproject.service.CategoryService;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.service.UserDetailsServiceImpl;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("mod/api/category")
public class ModCategoryApi {
    final CategoryService categoryService;
    final UserDetailsServiceImpl userDetailsService;
    final MessageResourceService messageResourceService;

    @GetMapping()
    public Page<Category> getList(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "status", required = false, defaultValue = "") Enums.CategoryStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            return categoryService.findAllByStatus(status, pageable);
        }
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Category getDetail(@PathVariable("id") long id) {
        Optional<Category> optionalCategory = categoryService.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return optionalCategory.get();
    }

    @PostMapping()
    public Category create(@RequestBody Category courseDto, Authentication principal) {
        String adminID = principal.getName();
        Optional<User> optionalUse = userDetailsService.findByUsername(adminID);
        if (!optionalUse.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = optionalUse.get();
        System.out.println(adminID);
        return categoryService.save(courseDto, user.getId());
    }

    @PutMapping()
    public String update(@RequestBody Category courseDto, Authentication principal) {
        String adminId = principal.getName();
        Optional<User> op = userDetailsService.findByUsername(adminId);
        if (!op.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = op.get();
        System.out.println(adminId);
        categoryService.update(courseDto, user.getId());
        return messageResourceService.getMessage("update.success");
    }
}
