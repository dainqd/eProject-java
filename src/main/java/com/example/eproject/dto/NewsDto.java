package com.example.eproject.dto;

import com.example.eproject.entity.Category;
import com.example.eproject.entity.News;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {
    private long id;
    private String title;
    private String summary;
    private String description;
    private String img;
    private String content;
    private String contain;
    private int views = 1;
    private int comments = 1;
    private List<String> tag;
    private Enums.NewsStatus status = Enums.NewsStatus.DEACTIVE;
    private String author;
    private Set<Category> categories = new HashSet<>();

    public NewsDto(News news) {
        BeanUtils.copyProperties(news, this);
        this.categories = news.getCategories();
    }
}
