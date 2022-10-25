package com.example.managerfeedback.restapi.admin;

import com.example.managerfeedback.entity.Feedbacks;
import com.example.managerfeedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("admin/api/feedbacks")
public class AdminFeedbackApi {

    @Autowired
    FeedbackService feedbackService;

    @GetMapping()
    public ResponseEntity<List<Feedbacks>> getLists(){
        return ResponseEntity.ok(feedbackService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetails(@PathVariable Integer id){
        Optional<Feedbacks> optionalFeedbacks = feedbackService.findById(id);
        if (!optionalFeedbacks.isPresent()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(feedbackService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Feedbacks> create(@RequestBody Feedbacks feedbacks,  Authentication principal){
        long id = Long.parseLong(principal.getName());
        return ResponseEntity.ok(feedbackService.save(feedbacks, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedbacks> update(@PathVariable Integer id, @RequestBody Feedbacks feedbacks, Authentication principal){
        long idM = Long.parseLong(principal.getName());
        Optional<Feedbacks> optionalFeedbacks = feedbackService.findById(id);
        if ((!optionalFeedbacks.isPresent())){
            ResponseEntity.badRequest().build();
        }
        Feedbacks existFeedbacks = optionalFeedbacks.get();

        existFeedbacks.setUpdatedAt(LocalDateTime.now());
        existFeedbacks.setUpdatedBy(idM);

        existFeedbacks.setUsername(feedbacks.getUsername());
        existFeedbacks.setStar(feedbacks.getStar());
        existFeedbacks.setContent(feedbacks.getContent());
        existFeedbacks.setStatus(feedbacks.getStatus());
        return ResponseEntity.ok(feedbackService.save(existFeedbacks, idM));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        if ((!feedbackService.findById(id).isPresent())){
            ResponseEntity.badRequest().build();
        }
        feedbackService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
