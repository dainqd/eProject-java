package com.example.eproject.dto;

import com.example.eproject.entity.Admissions;
import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import com.example.eproject.util.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionsDto {
    private long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String birthday;
    private String gender;
    private String address;
    private String nameOfParents;
    private String phoneOfParents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Long createdBy;
    private Long updatedBy;
    private Long deletedBy;
    private Enums.AdmissionsStatus status = Enums.AdmissionsStatus.PENDING;

    public AdmissionsDto(Admissions admissions) {
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(admissions.getBirthday());
        BeanUtils.copyProperties(admissions, this);
        this.setBirthday(todayAsString);
    }
}
