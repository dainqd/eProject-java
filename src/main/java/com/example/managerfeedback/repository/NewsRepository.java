package com.example.managerfeedback.repository;


import com.example.managerfeedback.entity.News;
import com.example.managerfeedback.util.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByStatus(Enums.NewsStatus status);

    Optional<News> findAllByIdAndStatus(long id, Enums.NewsStatus status);
}
