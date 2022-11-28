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
    private Set<Faculty> faculties = new HashSet<>();
    private Set<User> users = new HashSet<>();
    private Enums.ClassroomStatus status;

    public ClassroomDto(Classroom classroom) {
        BeanUtils.copyProperties(classroom, this);
    }
}
