package com.example.eproject.entity;

import com.example.eproject.dto.CourseDto;
import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Currency;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course")
public class Course extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    //    Mục tiêu
    private String intent;
    //    Điều kiện
    private String condition;
    //    Nội dung khóa học
    @Lob
    private String content;
    //    Bình luận
    private String comments;
    //    Nhận xét
    private String reviews;
    //    Giáo viên
    private String trainer;
    //    Học phí
    private Currency price;
    //    Chỗ ngồi
    private long seat;
    //    Thời gian bắt đầu
    private Date startDate;
    //    Thời gian Kết thúc
    private Date endDate;
    //    Chỗ trống
    private long free;

    @Enumerated(EnumType.STRING)
    private Enums.CourseStatus status = Enums.CourseStatus.PREACTIVE;

    public Course(CourseDto courseDto) {
        BeanUtils.copyProperties(courseDto, this);
    }
}
