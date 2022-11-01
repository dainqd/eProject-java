package com.example.eproject.service;

import com.example.eproject.entity.Feedbacks;
import com.example.eproject.repository.FeedbacksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    @Autowired
    private FeedbacksRepository feedbacksRepository;

    public List<Feedbacks> findAll(){
        return feedbacksRepository.findAll();
    }

    public Optional<Feedbacks> findById(long id){
        return feedbacksRepository.findById(id);
    }

    public Feedbacks save(Feedbacks feedbacks, long id) {
        feedbacks.setCreatedAt(LocalDateTime.now());
        feedbacks.setCreatedBy(id);
        return feedbacksRepository.save(feedbacks);
    }

    public void deleteById(long id){
        feedbacksRepository.deleteById(id);
    }

    public List<Feedbacks> getListByStatus(boolean status){
        return feedbacksRepository.findAllByStatus(status);
    }

    public Optional<Feedbacks> getListByIdAndStatus(long id, boolean status){
        return feedbacksRepository.findAllByIdAndStatus(id, status);
    }
}
