package com.example.eproject.service;

import com.example.eproject.dto.NewsDto;
import com.example.eproject.entity.News;
import com.example.eproject.repository.NewsRepository;
import com.example.eproject.util.Enums;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public Page<News> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    public Optional<News> findById(long id) {
        return newsRepository.findById(id);
    }

    public Page<News> findAllByStatus(Enums.NewsStatus status, Pageable pageable) {
        return newsRepository.findAllByStatus(status, pageable);
    }

    public List<News> findByStatus(Enums.NewsStatus status) {
        return newsRepository.findByStatus(status);
    }

    public Optional<News> findByIdAndStatus(long id, Enums.NewsStatus status) {
        return newsRepository.findByIdAndStatus(id, status);
    }

    public News save(News news) {
        return newsRepository.save(news);
    }

    public News create(NewsDto newsDto, long adminId) {
        News news = new News();
        BeanUtils.copyProperties(newsDto, news);
        news.setCreatedAt(LocalDateTime.now());
        news.setCreatedBy(adminId);
        return newsRepository.save(news);
    }

    public News update(NewsDto newsDto, long adminId) {
        Optional<News> optionalNews = newsRepository.findById(newsDto.getId());
        if (!optionalNews.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR");
        }

        News news = optionalNews.get();

        BeanUtils.copyProperties(newsDto, news);
        news.setUpdatedAt(LocalDateTime.now());
        news.setUpdatedBy(adminId);
        return newsRepository.save(news);
    }

    public void delete(long id, long adminID) {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (!optionalNews.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR");
        }
        News news = optionalNews.get();

        news.setDeletedAt(LocalDateTime.now());
        news.setDeletedBy(adminID);
        news.setStatus(Enums.NewsStatus.DELETED);
        newsRepository.save(news);
    }

    public void deleteById(long id) {
        newsRepository.deleteById(id);
    }
}