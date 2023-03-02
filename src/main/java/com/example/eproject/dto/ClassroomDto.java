package com.example.eproject.dto;

import com.example.eproject.entity.Classroom;
import com.example.eproject.entity.Faculty;
import com.example.eproject.entity.User;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomDto {
    private long id;
    private String title;
    private int qtyStudent;
    private long modId;
    private String content;
    private String note;
    private String startDate;
    private String endDate;
    private Set<Faculty> faculties = new HashSet<>();
    private Set<User> users = new HashSet<>();
    private Enums.ClassroomStatus status;

    public ClassroomDto(Classroom classroom) {
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String startDateAsString = df.format(classroom.getStartDate());
        String endDateAsString = df.format(classroom.getEndDate());
        BeanUtils.copyProperties(classroom, this);
        this.setStartDate(startDateAsString);
        this.setEndDate(endDateAsString);
    }
}
