package com.example.eproject.dto;

import com.example.eproject.entity.Course;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Currency;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private long id;
    private String title;
    private String intent;
    private String condition;
    private String content;
    private String comments;
    private String reviews;
    private String trainer;
    private Currency price;
    private long seat;
    private String startDate;
    private String endDate;
    private long free;
    @Enumerated(EnumType.STRING)
    private Enums.CourseStatus status = Enums.CourseStatus.PREACTIVE;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Long createdBy;
    private Long updatedBy;
    private Long deletedBy;

    public CourseDto(Course course) {
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String sTodayAsString = df.format(course.getStartDate());
        String eTodayAsString = df.format(course.getEndDate());
        BeanUtils.copyProperties(course, this);
        this.setStartDate(sTodayAsString);
        this.setEndDate(eTodayAsString);
    }
}
