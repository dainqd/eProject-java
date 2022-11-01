package com.example.eproject.service;

import com.example.eproject.dto.NewsDto;
import com.example.eproject.entity.News;
import com.example.eproject.repository.NewsRepository;
import com.example.eproject.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public Optional<News> findById(long id) {
        return newsRepository.findById(id);
    }

    public News save(News news) {
        return newsRepository.save(news);
    }

    public News create(NewsDto newsDto, long adminId) {
        News news = new News(newsDto);
        news.setCreatedAt(LocalDateTime.now());
        news.setCreatedBy(adminId);
        return newsRepository.save(news);
    }


    public void deleteById(long id) {
        newsRepository.deleteById(id);
    }

    public List<News> getListByStatus(Enums.NewsStatus status) {
        return newsRepository.findAllByStatus(status);
    }

    public Optional<News> getListByIdAndStatus(long id, Enums.NewsStatus status) {
        return newsRepository.findAllByIdAndStatus(id, status);
    }
}