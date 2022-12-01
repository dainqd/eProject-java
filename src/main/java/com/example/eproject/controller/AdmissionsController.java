package com.example.eproject.controller;

import com.example.eproject.dto.AdmissionsDto;
import com.example.eproject.service.AdmissionsService;
import com.example.eproject.service.MessageResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "admissions")
@Slf4j
public class AdmissionsController {
    final AdmissionsService admissionsService;
    final MessageResourceService messageResourceService;
    @GetMapping("list")
    public String addmission(Model model) {
        return "layout/admissions";
    }

    @PostMapping("signup")
    public ResponseEntity<?> create(@Valid @RequestBody AdmissionsDto admissionsDto) {
        try {
            admissionsService.create(admissionsDto);
            return new ResponseEntity<>(messageResourceService.getMessage("ok"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(messageResourceService.getMessage("error"), HttpStatus.BAD_REQUEST);
        }
    }
}
