package com.example.eproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CredentialDto {
    private long accountId;
    private String accountUsername;
    private String accessToken;
    private String refreshToken;
    private long expiredAt;
    private String scope;
}
