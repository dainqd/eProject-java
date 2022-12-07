package com.example.eproject.entity;

import com.example.eproject.dto.ManagerDto;
import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "managers")
public class Manager extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    private long feedbacks;
    private String summary;
    //    Chức vụ
    private String position;
    //    Giới thiệu
    private String introduce;
    @Email(message = "Incorrect email format!, Please re-enter")
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Enums.ManagerStatus status = Enums.ManagerStatus.ACTIVE;

    public Manager(ManagerDto managerDto) {
        BeanUtils.copyProperties(managerDto, this);
    }
}
