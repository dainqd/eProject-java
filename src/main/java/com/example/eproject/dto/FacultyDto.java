package com.example.eproject.dto;

import com.example.eproject.entity.Faculty;
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
public class FacultyDto {
    private long id;
    private String title;
    private String content;
    private Enums.FacultyStatus status;

    public FacultyDto(Faculty faculty) {
        BeanUtils.copyProperties(faculty, this);
    }
}
