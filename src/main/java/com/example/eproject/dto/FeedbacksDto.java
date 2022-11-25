package com.example.eproject.dto;

import com.example.eproject.entity.Feedbacks;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbacksDto {
    private long id;
    private String name;
    private String email;
    private String subject;
    private String message;
    private Enums.FeedbackStatus status;
    private long userID;
    public FeedbacksDto(Feedbacks feedbacks) {
        BeanUtils.copyProperties(feedbacks, this);
    }
}
