package com.example.eproject.dto;

import com.example.eproject.entity.Admissions;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AdmissionsDto {
    private long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private Date birthday;

    private String gender;

    private String address;

    private String nameOfParents;

    private String phoneOfParents;

    private Enums.AdmissionsStatus status = Enums.AdmissionsStatus.PENDING;
    public AdmissionsDto(Admissions admissions){
        BeanUtils.copyProperties(admissions, this);
    }
}
