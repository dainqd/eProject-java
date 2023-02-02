package com.example.eproject.entity;

import com.example.eproject.dto.CourseDto;
import com.example.eproject.entity.base.StringListConverter;
import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

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
    @Lob
    private String thumbnail;
    private String title;
    //    Mục tiêu
    @Lob
    private String intent;
    //    Điều kiện
    @Lob
    private String condition;
    //    Nội dung khóa học
    @Lob
    private String content;
    //    Bình luận
    private long comments;
    //    Nhận xét
    private long reviews;
    //    Giáo viên
    private String trainer;
    //    Học phí
    private String price;
    //    Chỗ ngồi
    private long seat;
    //    Thời gian bắt đầu
    private Date startDate;
    //    Thời gian Kết thúc
    private Date endDate;
    //    Chỗ trống
    private long free;
    //
    @Convert(converter = StringListConverter.class)
    private List<String> outline;
    @Enumerated(EnumType.STRING)
    private Enums.CourseStatus status = Enums.CourseStatus.PREACTIVE;

    public Course(CourseDto courseDto) {
        BeanUtils.copyProperties(courseDto, this);
    }
}
