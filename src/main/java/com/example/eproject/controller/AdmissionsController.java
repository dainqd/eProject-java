//package com.example.eproject.controller;
//
//import com.example.eproject.dto.AdmissionsDto;
//import com.example.eproject.service.AdmissionsService;
//import com.example.eproject.util.Enums;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping(path = "admissions")
//@Slf4j
//public class AdmissionsController {
//    final AdmissionsService admissionsService;
//    @GetMapping("list")
//    public String getList(Model model,
//                          @RequestParam(value = "page", required = false, defaultValue = "0") int page,
//                          @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
//        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by("id").descending());
//        Page<AdmissionsDto> admissionsDtos = admissionsService.findAllByStatus(Enums.AdmissionsStatus.APPROVED, pageable).map(admissionsDtos::new);
//        model.addAttribute("recruits", admissionsDtos);
//        return "v2/recruit/list";
//    }
//
//    @GetMapping("detail/{id}")
//    public String getDetail(Model model, @PathVariable long id) {
////
//        List<Recruit> lRecruit = recruitService.findAllByStatus(Enums.RecruitStatus.ACTIVE);
////
//        Optional<Recruit> optionalRecruit = recruitService.findByIdAndStatus(id, Enums.RecruitStatus.ACTIVE);
//        if (!optionalRecruit.isPresent()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageResourceService.getMessage("id.notfound"));
//        }
//        optionalRecruit.get().setViews(optionalRecruit.get().getViews() + 1);
//        recruitService.save(optionalRecruit.get());
//        RecruitDto recruitDto = new RecruitDto(optionalRecruit.get());
//        long days = LocalDateTime.now().until(recruitDto.getEndDate(), ChronoUnit.DAYS);
//
//        DateTimeFormatter.ofPattern("dd/MM/yyyy").format(recruitDto.getStartDate());
//        DateTimeFormatter.ofPattern("dd/MM/yyyy").format(recruitDto.getEndDate());
//
//        if (days < 0 || days == 0) {
//            optionalRecruit.get().setStatus(Enums.RecruitStatus.DELETE);
//            recruitService.save(optionalRecruit.get());
//        } else {
//            optionalRecruit.get().setStatus(Enums.RecruitStatus.ACTIVE);
//            recruitService.save(optionalRecruit.get());
//            model.addAttribute("recruit", recruitDto);
//            model.addAttribute("date", days);
//            model.addAttribute("recruits", lRecruit);
//            model.addAttribute("sDate", DateTimeFormatter.ofPattern("dd/MM/yyyy").format(recruitDto.getStartDate()));
//            model.addAttribute("eDate", DateTimeFormatter.ofPattern("dd/MM/yyyy").format(recruitDto.getEndDate()));
//        }
//        return "v2/recruit/detail";
//    }
//}
