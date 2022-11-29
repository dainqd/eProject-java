package com.example.eproject.entity;

import com.example.eproject.dto.MarkReportDto;
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
@Table(name = "markreport")
// Báo cáo điểm
public class MarkReport extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private long studentId;
    private String subject;
    private long mark;
    @Enumerated(EnumType.STRING)
    private Enums.PointStatus aspect = Enums.PointStatus.NOTSTART;
    @Enumerated(EnumType.STRING)
    private Enums.MarkReportStatus status;
    private String note;

    public MarkReport(MarkReportDto markReportDto) {
        BeanUtils.copyProperties(markReportDto, this);
    }
}