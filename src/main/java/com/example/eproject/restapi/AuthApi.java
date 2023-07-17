package com.example.eproject.restapi;

import com.example.eproject.dto.reponse.JwtResponse;
import com.example.eproject.dto.request.LoginRequest;
import com.example.eproject.dto.request.SignupRequest;
import com.example.eproject.entity.Role;
import com.example.eproject.entity.User;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.service.RoleService;
import com.example.eproject.service.UserDetailsIpmpl;
import com.example.eproject.service.UserDetailsServiceImpl;
import com.example.eproject.util.Enums;
import com.example.eproject.util.JwtUtils;
import com.google.gson.Gson;
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

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
        System.out.println("aaaaaaaaaaaa");
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
        System.out.println(new Gson().toJson(account));
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
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleService.findByName(Enums.Role.USER)
                    .orElseThrow(() -> new RuntimeException(MESS_ERR_ROLE));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findByName(Enums.Role.ADMIN)
                                .orElseThrow(() -> new RuntimeException(MESS_ERR_ROLE));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleService.findByName(Enums.Role.MODERATOR)
                                .orElseThrow(() -> new RuntimeException(MESS_ERR_ROLE));
                        roles.add(modRole);
                        break;
                    case "teach":
                        Role teachRole = roleService.findByName(Enums.Role.TEACHER)
                                .orElseThrow(() -> new RuntimeException(MESS_ERR_ROLE));
                        roles.add(teachRole);
                        break;
                    case "student":
                        Role studentRole = roleService.findByName(Enums.Role.STUDENT)
                                .orElseThrow(() -> new RuntimeException(MESS_ERR_ROLE));
                        roles.add(studentRole);
                        break;
                    default:
                        Role userRole = roleService.findByName(Enums.Role.USER)
                                .orElseThrow(() -> new RuntimeException(MESS_ERR_ROLE));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        user.setVerified(false);
        user.setStatus(Enums.AccountStatus.DEACTIVE);
        userDetailsService.save(user);
        return ResponseEntity.ok(messageResourceService.getMessage("register.success"));
    }
}

