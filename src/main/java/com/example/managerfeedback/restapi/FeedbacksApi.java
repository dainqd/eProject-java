package com.example.managerfeedback.restapi;

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
@RequestMapping("/api/feedbacks")
public class FeedbacksApi {

    @Autowired
    FeedbackService feedbackService;

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Feedbacks>> getList(){
        return ResponseEntity.ok(feedbackService.getListByStatus(true));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getDetail(@PathVariable Integer id){
        Optional<Feedbacks> optionalFeedbacks = feedbackService.getListByIdAndStatus(id, true);
        if (!optionalFeedbacks.isPresent()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(feedbackService.getListByIdAndStatus(id, true));
    }

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Feedbacks> create(@RequestBody Feedbacks feedbacks,  Authentication principal){
        long id = Long.parseLong(principal.getName());
        return ResponseEntity.ok(feedbackService.save(feedbacks, id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
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
}
