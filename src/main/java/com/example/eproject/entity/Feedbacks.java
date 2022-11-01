package com.example.eproject.entity;

import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedbacks")
public class Feedbacks extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    @Max(value=5, message = "Please re-enter the number of review stars ")
    private int star;

    @Column(columnDefinition="text")
    private String content;

    @Enumerated(EnumType.STRING)
    private Enums.FeedbackStatus status;
}
