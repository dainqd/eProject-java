package com.example.eproject.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.eproject.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper class to handle jwt token
 * */
@Component
public class JwtUtils {
//    @Value("${bezkoder.app.jwtSecret}")
    public static String jwtSecret = "bezKoderSecretKey";

//    @Value("${bezkoder.app.jwtExpirationMs}")
    public int jwtExpirationsMs = 86422222;

//    @Value("${bezkoder.app.issuer}")
    public String issuer = "issure";
    // some time units constant
    public static final int ONE_SECOND = 1000;
    public static final int ONE_MINUTE = ONE_SECOND * 60;
    public static final int ONE_HOUR = ONE_MINUTE * 60;
    public static final int ONE_DAY = ONE_HOUR * 24;
    public static final String ROLES_CLAIM_KEY = "roles";
    public static final String EMAIL_CLAIM_KEY = "email";
    public static final String FIRST_NAME_CLAIM_KEY = "firstName";
    public static final String LAST_NAME_CLAIM_KEY = "lastName";

    public JwtUtils() {}

    public JwtUtils(String secretKey) {
        jwtSecret = secretKey;
    }

    public static JwtUtils getInstance() {
        return new JwtUtils();
    }

    public static JwtUtils getInstance(String secretKey) {
        return new JwtUtils(secretKey);
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtSecret.getBytes());
    }

    public JWTVerifier getVerifier() {
        return JWT.require(getAlgorithm()).build();
    }

    public void getDecodedJwt(String token) {
        getVerifier().verify(token);
    }

    public String generateToken(User user) {
        List<String> list = new ArrayList<>();
        user.getRoles().forEach(role -> list.add(role.getName().name()));
        return JWT.create()
                .withSubject(String.valueOf(user.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationsMs))
                .withIssuer(issuer)
                .withClaim(JwtUtils.ROLES_CLAIM_KEY, list)
                .withClaim(JwtUtils.EMAIL_CLAIM_KEY, user.getEmail())
                .withClaim(JwtUtils.FIRST_NAME_CLAIM_KEY, user.getFirstName())
                .withClaim(JwtUtils.LAST_NAME_CLAIM_KEY, user.getLastName())
                .sign(getAlgorithm());
    }

    /**
     * Tạo token cho người dùng ở Divega nhưng kèm thông tin token xịn ở Account Metaworld
     * */
    public String generateToken(User user, String issuer, String accessToken, int expireAfter) {
        List<String> list = new ArrayList<>();
        user.getRoles().forEach(role -> list.add(role.getName().name()));
        return JWT.create()
                .withSubject(String.valueOf(user.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expireAfter))
                .withIssuer(issuer)
                .withClaim(JwtUtils.ROLES_CLAIM_KEY, list)
                .withClaim(JwtUtils.EMAIL_CLAIM_KEY, user.getEmail())
                .withClaim(JwtUtils.FIRST_NAME_CLAIM_KEY, user.getFirstName())
                .withClaim(JwtUtils.LAST_NAME_CLAIM_KEY, user.getLastName())
                .sign(getAlgorithm());
    }
}