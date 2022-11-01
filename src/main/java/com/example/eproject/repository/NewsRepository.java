package com.example.eproject.repository;


import com.example.eproject.entity.News;
import com.example.eproject.util.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByStatus(Enums.NewsStatus status);

    Optional<News> findAllByIdAndStatus(long id, Enums.NewsStatus status);
}
