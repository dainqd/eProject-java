package com.example.eproject.entity;

import com.example.eproject.dto.AdmissionsDto;
import com.example.eproject.dto.FacultyDto;
import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "faculty")
public class Faculty extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Enums.FacultyStatus status;

    public Faculty(FacultyDto facultyDto) {
        BeanUtils.copyProperties(facultyDto, this);
    }
}
