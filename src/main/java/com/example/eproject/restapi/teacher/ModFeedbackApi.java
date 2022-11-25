package com.example.eproject.restapi.teacher;

import com.example.eproject.dto.FeedbacksDto;
import com.example.eproject.entity.Feedbacks;
import com.example.eproject.entity.User;
import com.example.eproject.service.FeedbackService;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("mod/api/feedbacks")
public class ModFeedbackApi {
    @Autowired
    FeedbackService feedbackService;
    @Autowired
    MessageResourceService messageResourceService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

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
}
