package com.example.eproject.restapi;

import com.example.eproject.dto.request.LoginRequest;
import com.example.eproject.dto.request.SignupRequest;
import com.example.eproject.entity.Credential;
import com.example.eproject.entity.User;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.service.RoleService;
import com.example.eproject.service.UserDetailsIpmpl;
import com.example.eproject.service.UserDetailsServiceImpl;
import com.example.eproject.util.Enums;
import com.example.eproject.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthApi {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    RoleService roleService;

    @Autowired
    MessageResourceService messageResourceService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public Credential authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
        Optional<User> optionalUser = userDetailsService.findByUsername(loginRequest.getUsername());
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.not.found"));
        }
        User account = optionalUser.get();
        if (!account.isVerified()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.not.verified"));
        }
        if (account.getStatus() == Enums.AccountStatus.DEACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.not.active"));
        }
        if (account.getStatus() == Enums.AccountStatus.BLOCKED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.banned"));
        }
        if (account.getStatus() == Enums.AccountStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.deleted"));
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                        , loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateToken(authentication);

        int expiredAfterDay = 7;
        String accessToken =
                jwtUtils.generateTokenByAccount(authentication, account, expiredAfterDay = 24 * 60 * 60 * 1000);
        String refreshToken =
                jwtUtils.generateTokenByAccount(authentication, account, 14 * 24 * 60 * 60 * 1000);

        UserDetailsIpmpl userDetails = (UserDetailsIpmpl) authentication.getPrincipal();

        Credential credential = new Credential();
        credential.setAccessToken(accessToken);
        credential.setRefreshToken(refreshToken);
        credential.setExpiredAt(expiredAfterDay);
        credential.setScope("basic_information");
        credential.setAccountId(account.getId());
        credential.setAccountUsername(account.getUsername());

        return credential;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest) {
        Optional<User> optionalUser = userDetailsService.findByUsername(signupRequest.getUsername());
        if (optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.username.exist"));
        }
        Optional<User> userOptional = userDetailsService.findByEmail(signupRequest.getEmail());
        if (userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.email.exist"));
        }
        if (signupRequest.getPassword().length() < 6 || Objects.equals(signupRequest.getPassword(), "")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.password.invalid"));
        }
        if (!Objects.equals(signupRequest.getPassword(), signupRequest.getPasswordConfirm())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.password.incorrect"));
        }
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));
        user.setRole(Enums.Role.USER);
        user.setVerified(false);
        user.setStatus(Enums.AccountStatus.DEACTIVE);
        userDetailsService.save(user);
        return ResponseEntity.ok(messageResourceService.getMessage("register.success"));
    }
}

