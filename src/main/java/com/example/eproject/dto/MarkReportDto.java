package com.example.eproject.dto;

import com.example.eproject.entity.MarkReport;
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
public class MarkReportDto {
    private long id;
    private String name;
    private long studentId;
    private String subject;
    private long mark;
    private Enums.PointStatus aspect = Enums.PointStatus.NOTSTART;
    private Enums.MarkReportStatus status;

    public MarkReportDto(MarkReport markReport) {
        BeanUtils.copyProperties(markReport, this);
    }
}
