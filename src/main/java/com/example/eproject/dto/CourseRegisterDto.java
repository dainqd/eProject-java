package com.example.eproject.dto;

import com.example.eproject.entity.CourseRegister;
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
public class CourseRegisterDto {
    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String courseName;
    private Enums.CourseRegisterStatus status = Enums.CourseRegisterStatus.PENDING;

    public CourseRegisterDto(CourseRegister courseRegister) {
        BeanUtils.copyProperties(courseRegister, this);
    }
}
