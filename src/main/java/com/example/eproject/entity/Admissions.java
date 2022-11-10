package com.example.eproject.entity;

import com.example.eproject.dto.AdmissionsDto;
import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import com.example.eproject.util.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admissions")
public class Admissions extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullName;

    @Email(message = "Incorrect email format!, Please re-enter")
    private String email;

    private String phoneNumber;

    private Date birthday;

    private String gender;

    private String address;

    private String nameOfParents;

    private String phoneOfParents;

    @Enumerated(EnumType.STRING)
    private Enums.AdmissionsStatus status = Enums.AdmissionsStatus.PENDING;

    public Admissions(AdmissionsDto admissionsDto){
        BeanUtils.copyProperties(admissionsDto, this);
    }
}
