package com.example.eproject.dto;

import com.example.eproject.entity.Admissions;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import java.text.DateFormat;
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
    private String linkFaceBook;
    private String message;
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
