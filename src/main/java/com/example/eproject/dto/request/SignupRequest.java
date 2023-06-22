package com.example.eproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private String verifyCode;
    private String passwordConfirm;

    public SignupRequest() {
        this.username = "";
        this.email = "";
        this.password = "";
        this.verifyCode = "";
        this.passwordConfirm = "";
    }
}
