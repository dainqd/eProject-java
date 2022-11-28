package com.example.eproject.entity;

import com.example.eproject.dto.ClassroomDto;
import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "classroom")
public class Classroom extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private int qtyStudent;
    private long modId;
    private String content;
    private String note;
    private Date startDate;
    private Date endDate;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "classroom_faculties", joinColumns = @JoinColumn(name = "classroom_id")
            , inverseJoinColumns = @JoinColumn(name = "faculty_id"))
    private Set<Faculty> faculties = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "classroom_users", joinColumns = @JoinColumn(name = "classroom_id")
            , inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Set<User> users = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private Enums.ClassroomStatus status;

    public Classroom(ClassroomDto classroomDto) {
        BeanUtils.copyProperties(classroomDto, this);
    }
}
