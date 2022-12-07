package com.example.eproject.dto;

import com.example.eproject.entity.Manager;
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
public class ManagerDto {
    private long id;
    private String fullName;
    private long feedbacks;
    private String summary;
    //    Chức vụ
    private String position;
    //    Giới thiệu
    private String introduce;
    private String email;
    private String phoneNumber;
    private Enums.ManagerStatus status = Enums.ManagerStatus.ACTIVE;

    public ManagerDto(Manager manager) {
        BeanUtils.copyProperties(manager, this);
    }
}
