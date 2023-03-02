package com.example.eproject.repository;

import com.example.eproject.entity.News;
import com.example.eproject.util.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Page<News> findAll(Pageable pageable);

    Page<News> findAllByStatus(Enums.NewsStatus status, Pageable pageable);

    Optional<News> findById(long id);

    Optional<News> findByIdAndStatus(long id, Enums.NewsStatus status);

    List<News> findByStatus(Enums.NewsStatus status);
}
