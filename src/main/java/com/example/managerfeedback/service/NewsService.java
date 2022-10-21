package com.example.managerfeedback.service;

import com.example.managerfeedback.dto.NewsDto;
import com.example.managerfeedback.entity.Category;
import com.example.managerfeedback.entity.News;
import com.example.managerfeedback.repository.NewsRepository;
import com.example.managerfeedback.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Optional<News> getListByIdAndStatus(Integer id, Enums.NewsStatus status) {
        return newsRepository.findAllByIdAndStatus(id, status);
    }
}