package com.example.eproject.entity;

import com.example.eproject.dto.CourseRegisterDto;
import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courseRegister")
public class CourseRegister extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String phoneNumber;
    private String courseName;
    @Enumerated(EnumType.STRING)
    private Enums.CourseRegisterStatus status = Enums.CourseRegisterStatus.PENDING;

    public CourseRegister(CourseRegisterDto courseRegisterDto) {
        BeanUtils.copyProperties(courseRegisterDto, this);
    }
}
