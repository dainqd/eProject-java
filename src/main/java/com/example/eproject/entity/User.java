package com.example.eproject.entity;

import com.example.eproject.dto.EventsDto;
import com.example.eproject.dto.UserDto;
import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Lob
    private String avt;
    private String firstName;
    private String lastName;
    private String username;
    @Email(message = "Incorrect email format!, Please re-enter")
    private String email;
    private String phoneNumber;
    private Date birthday;
    private String gender;
    private String address;
    private String verifyCode = "";
    private String referralCode = "";
    private boolean verified = false;
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    private Enums.Role role = Enums.Role.USER;
    @Enumerated(EnumType.STRING)
    private Enums.AccountStatus status;
    public User(String username, String email,String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(UserDto userDto) {
        BeanUtils.copyProperties(userDto, this);
    }
}