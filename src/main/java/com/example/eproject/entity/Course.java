package com.example.eproject.entity;

import com.example.eproject.entity.basic.BasicEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private int seat;
    //    Thời gian bắt đầu
    private Date startDate;
    //    Thời gian Kết thúc
    private Date endDate;
}
