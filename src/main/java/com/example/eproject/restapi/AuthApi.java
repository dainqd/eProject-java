package com.example.eproject.restapi;

import com.example.eproject.dto.reponse.JwtResponse;
import com.example.eproject.dto.request.LoginRequest;
import com.example.eproject.dto.request.SignupRequest;
import com.example.eproject.entity.User;
import com.example.eproject.service.*;
import com.example.eproject.util.Enums;
import com.example.eproject.util.JwtUtils;
import com.example.eproject.util.Utils;
import lombok.RequiredArgsConstructor;
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

import javax.servlet.http.Cookie;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthApi {
    public static final String MESS_ERR_ROLE = "messageResourceService.getMessage(\"role.not.found\")";
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

    final JwtUtils jwtUtils;
    final EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
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
        // Xử lý check mật khẩu, add login history, update last login.
        boolean isMatch = userDetailsService.checkPasswordMatch(loginRequest.getPassword(), account);
        if (isMatch) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                            , loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateToken(account);

            UserDetailsIpmpl userDetails = (UserDetailsIpmpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            String message = "Success";
            return ResponseEntity.ok(new JwtResponse(message,
                    jwt,
                    userDetails.getId(),
                    userDetails.getAvt(),
                    userDetails.getFirstname(),
                    userDetails.getLastName(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getPhoneNumber(),
                    userDetails.getBirthday(),
                    userDetails.getGender(),
                    userDetails.getAddress(),
                    roles));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.password.incorrect"));
        }

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
        String verifyCode = Utils.generatorVerifyCode(6);
        userDetailsService.create(signupRequest, verifyCode);
        emailService.userRegisterMail(signupRequest.getEmail(), verifyCode);

        return ResponseEntity.ok(messageResourceService.getMessage("account.verify"));
    }

    @PostMapping("/verify-signup")
    public ResponseEntity<?> verifySignUp(@Validated @RequestBody SignupRequest signupRequest) {
        Optional<User> optionalUser = userDetailsService.findByUsername(signupRequest.getUsername());
        if (signupRequest.getVerifyCode().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.verify.empty"));
        }
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.not.found"));
        }
        User account = optionalUser.get();
        if (account.isVerified()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.verified"));
        }
        if (!userDetailsService.checkVerifyCode(account, signupRequest.getVerifyCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.verifycode.incorrect"));
        }
        userDetailsService.active(account);
        return ResponseEntity.ok(messageResourceService.getMessage("register.success"));
    }
}

