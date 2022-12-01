package com.example.eproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ForgotPasswordRequest {
    private String email;

    public ForgotPasswordRequest() {
        this.email = "";
    }
}
