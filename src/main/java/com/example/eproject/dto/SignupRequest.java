package com.example.eproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String avt;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
//    private String phoneNumber;
    private Date birthday;
    private String gender;
//    private String address;
    private String password;
    private Set<String> role;
}
