package com.example.eproject.entity;

import com.example.eproject.dto.FeedbacksDto;
import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;

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
    private String name;
    @Email
    private String email;
    private String subject;
    @Lob
    private String message;
    @Enumerated(EnumType.STRING)
    private Enums.FeedbackStatus status;
    private long userID;
    public Feedbacks(FeedbacksDto feedbacksDto) {
        BeanUtils.copyProperties(feedbacksDto, this);
    }
}
