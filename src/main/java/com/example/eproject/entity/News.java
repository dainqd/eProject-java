package com.example.eproject.entity;

import com.example.eproject.dto.NewsDto;
import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "news")
public class News extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String title;
    @Lob
    public String description;
    @Lob
    public String img;
    @Lob
    public String content;
    public int views = 1;
    @Enumerated(EnumType.STRING)
    public Enums.NewsStatus status;
    public String author;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "news_categories", joinColumns = @JoinColumn(name = "news_id")
            , inverseJoinColumns = @JoinColumn(name = "category_id"))
    public Set<Category> categories = new HashSet<>();

    public News(NewsDto request) {
        BeanUtils.copyProperties(request, this);
    }
}