package com.example.managerfeedback.dto;

import com.example.managerfeedback.entity.Category;
import com.example.managerfeedback.entity.News;
import com.example.managerfeedback.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {
    private long id;
    private String title;
    private String description;
    private String img;
    private String content;
    private int views = 1;
    private Enums.NewsStatus status;
    private String author;
    private Set<Category> categories = new HashSet<>();
    public NewsDto(News news) {
        BeanUtils.copyProperties(news, this);
        this.categories = news.getCategories();
    }
}
