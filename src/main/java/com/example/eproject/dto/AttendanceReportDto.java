package com.example.eproject.dto;

import com.example.eproject.entity.AttendanceReport;
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
public class AttendanceReportDto {
    private long id;
    private String name;
    private long studentID;
    private String lesson;
    private long teacherID;
    private Enums.Condition condition = Enums.Condition.ABSENT;
    private Enums.AttendanceStatus status = Enums.AttendanceStatus.ACTIVE;

    public AttendanceReportDto(AttendanceReport attendanceReport) {
        BeanUtils.copyProperties(attendanceReport, this);
    }
}
