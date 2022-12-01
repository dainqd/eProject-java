package com.example.eproject.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.eproject.entity.User;
import com.example.eproject.service.UserDetailsIpmpl;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


@Slf4j
@Component
public class JwtUtils {

    @Value("${bezkoder.app.jwtSecret}")
    public String jwtSecret;

    @Value("${bezkoder.app.jwtExpirationMs}")
    public int jwtExpirationsMs;

    private static String SECRET_KEY = "xxx";

    public String generateToken(Authentication authentication) {
        UserDetailsIpmpl userPrincipal = (UserDetailsIpmpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationsMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public DecodedJWT getDecodedJwt(String token) {
        return getVerifier().verify(token);
    }

    public JWTVerifier getVerifier() {
        return JWT.require(getAlgorithm()).build();
    }

//    public String generateToken(Authentication authentication) {
//        UserDetailsIpmpl userPrincipal = (UserDetailsIpmpl) authentication.getPrincipal();
//        return JWT.create()
//                .withSubject(String.valueOf(userPrincipal.getId()))
//                .withExpiresAt(new Date((new Date()).getTime() + jwtExpirationsMs))
//                .sign(getAlgorithm());
//    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SECRET_KEY.getBytes());
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expire: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claim string is empty: {}", e.getMessage());
        }
        return false;
    }
}

