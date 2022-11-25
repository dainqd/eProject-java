package com.example.eproject.restapi.admin;

import com.example.eproject.dto.FeedbacksDto;
import com.example.eproject.entity.Feedbacks;
import com.example.eproject.entity.User;
import com.example.eproject.service.FeedbackService;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.service.UserDetailsServiceImpl;
import com.example.eproject.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("admin/api/feedbacks")
public class AdminFeedbackApi {

    @Autowired
    FeedbackService feedbackService;
    @Autowired
    MessageResourceService messageResourceService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping()
    public Page<FeedbacksDto> getList(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "status", required = false, defaultValue = "") Enums.FeedbackStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            return feedbackService.findAllByStatus(status, pageable).map(FeedbacksDto::new);
        }
        return feedbackService.findAll(pageable).map(FeedbacksDto::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetails(@PathVariable Integer id) {
        Optional<Feedbacks> optionalFeedbacks = feedbackService.findById(id);
        if (!optionalFeedbacks.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return ResponseEntity.ok(feedbackService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Feedbacks> create(@RequestBody FeedbacksDto feedbacksDto, Authentication principal) {
        String adminID = principal.getName();
        Optional<User> optionalUse = userDetailsService.findByUsername(adminID);
        if (!optionalUse.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = optionalUse.get();
        System.out.println(adminID);
        return ResponseEntity.ok(feedbackService.save(feedbacksDto, user.getId()));
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, @RequestBody FeedbacksDto feedbacksDto, Authentication principal) {
        String adminID = principal.getName();
        Optional<User> optionalUse = userDetailsService.findByUsername(adminID);
        if (!optionalUse.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = optionalUse.get();
        Optional<Feedbacks> optionalFeedbacks = feedbackService.findById(id);
        if ((!optionalFeedbacks.isPresent())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("feedbacks.not.found"));
        }
        feedbackService.update(feedbacksDto, user.getId());
        return messageResourceService.getMessage("update.success");
    }

    @DeleteMapping()
    public ResponseEntity<String> delete(Feedbacks feedbacks) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String adminId = principal.getName();
        Optional<User> op = userDetailsService.findByUsername(adminId);
        if (!op.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        User user = op.get();
        System.out.println(adminId);
        Optional<Feedbacks> optionalFeedbacks = feedbackService.findById(feedbacks.getId());
        if (!optionalFeedbacks.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("feedbacks.not.found"));
        }
        feedbackService.delete(optionalFeedbacks.get(), user.getId());
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if ((!feedbackService.findById(id).isPresent())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("feedbacks.not.found"));
        }
        feedbackService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
