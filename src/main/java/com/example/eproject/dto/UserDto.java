package com.example.eproject.dto;

import com.example.eproject.entity.CourseRegister;
import com.example.eproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    private String avt;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private Date birthday;
    private String gender;
    private String address;
    private String verifyCode = "";
    private String referralCode = "";
    private boolean verified = false;
    private String password;
    private String role;
    private String status;

    public UserDto(User user) {
        BeanUtils.copyProperties(user, this);
    }
}
