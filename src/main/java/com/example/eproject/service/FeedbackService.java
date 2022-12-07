package com.example.eproject.service;

import com.example.eproject.dto.FeedbacksDto;
import com.example.eproject.entity.Feedbacks;
import com.example.eproject.entity.User;
import com.example.eproject.repository.FeedbacksRepository;
import com.example.eproject.util.Enums;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FeedbackService {
    @Autowired
    private FeedbacksRepository feedbacksRepository;
    @Autowired
    private MessageResourceService messageResourceService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public Page<Feedbacks> findAll(Pageable pageable) {
        return feedbacksRepository.findAll(pageable);
    }

    public Page<Feedbacks> findAllByStatus(Enums.FeedbackStatus status, Pageable pageable) {
        return feedbacksRepository.findAllByStatus(status, pageable);
    }

    public Optional<Feedbacks> findById(long id) {
        return feedbacksRepository.findById(id);
    }

    public Feedbacks create(FeedbacksDto feedbacksDto, long id) {
        Feedbacks feedbacks = new Feedbacks(feedbacksDto);
        BeanUtils.copyProperties(feedbacksDto, feedbacks);
        feedbacks.setUserID(id);
        feedbacks.setCreatedAt(LocalDateTime.now());
        feedbacks.setCreatedBy(id);
        return feedbacksRepository.save(feedbacks);
    }

    public Feedbacks save(FeedbacksDto feedbacksDto) {
        Feedbacks feedbacks = new Feedbacks(feedbacksDto);
        BeanUtils.copyProperties(feedbacksDto, feedbacks);
        feedbacks.setCreatedAt(LocalDateTime.now());
        feedbacks.setUserID(1L);
        feedbacks.setCreatedBy(1L);
        return feedbacksRepository.save(feedbacks);
    }

    public Feedbacks update(FeedbacksDto feedbacksDto, long adminID) {
        Optional<Feedbacks> optionalFeedbacks = feedbacksRepository.findById(feedbacksDto.getId());
        if (!optionalFeedbacks.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("feedbacks.not.found"));
        }
        Feedbacks feedbacks = optionalFeedbacks.get();

        BeanUtils.copyProperties(feedbacksDto, feedbacks);

        feedbacks.setUpdatedAt(LocalDateTime.now());
        feedbacks.setUpdatedBy(adminID);
        return feedbacksRepository.save(feedbacks);
    }

    public void delete(Feedbacks feedbacks, long adminID) {
        feedbacks.setStatus(Enums.FeedbackStatus.DELETED);
        feedbacks.setDeletedAt(LocalDateTime.now());
        feedbacks.setDeletedBy(adminID);
        feedbacksRepository.save(feedbacks);
    }

    public void deleteById(long id) {
        feedbacksRepository.deleteById(id);
    }
}
