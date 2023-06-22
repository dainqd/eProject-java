package com.example.eproject.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credential {
    private long accountId;
    private String accountUsername;
    private String accessToken;
    private String refreshToken;
    private long expiredAt;
    private String scope;
}
