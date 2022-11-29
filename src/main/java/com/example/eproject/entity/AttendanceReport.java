package com.example.eproject.entity;

import com.example.eproject.dto.AttendanceReportDto;
import com.example.eproject.dto.MarkReportDto;
import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

// Báo cáo điểm danh
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendancereport")
public class AttendanceReport extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private long studentID;
    private String lesson;
    private long teacherID;
    @Enumerated(EnumType.STRING)
    private Enums.Condition condition = Enums.Condition.ABSENT;
    @Enumerated(EnumType.STRING)
    private Enums.AttendanceStatus status = Enums.AttendanceStatus.ACTIVE;

    public AttendanceReport(AttendanceReportDto attendanceReportDto) {
        BeanUtils.copyProperties(attendanceReportDto, this);
    }
}
