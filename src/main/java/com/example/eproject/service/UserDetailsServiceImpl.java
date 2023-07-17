package com.example.eproject.service;

import com.example.eproject.dto.request.ForgotPasswordRequest;
import com.example.eproject.dto.request.SignupRequest;
import com.example.eproject.entity.Role;
import com.example.eproject.entity.User;
import com.example.eproject.repository.RoleRepository;
import com.example.eproject.repository.UserRepository;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.eproject.util.Utils.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
//    final EmailService emailService;
//    @Autowired
//    EmailService emailService;

    public static final String ACCESS_TOKEN_KEY = "accessToken";
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByVerifyCode(String code) {
        return userRepository.findByVerifyCode(code);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public boolean checkPasswordMatch(String rawPassword, User user) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return UserDetailsIpmpl.build(user);
    }

    public void createReferralCode(User account) {
        String referral = decimalToHex(Long.parseLong(generatorVerifyCode(8))) + decimalToHex(account.getId());
        account.setReferralCode(referral);
        userRepository.save(account);
    }

    public User create(SignupRequest request, String verifyCode) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setVerifyCode(verifyCode);
        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(Enums.Role.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(Enums.Role.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(Enums.Role.MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    case "teach":
                        Role teachRole = roleRepository.findByName(Enums.Role.TEACHER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(teachRole);
                        break;
                    case "student":
                        Role studentRole = roleRepository.findByName(Enums.Role.STUDENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(studentRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(Enums.Role.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        createReferralCode(user);
        return user;
    }

    public void responseCookieToEverySubdomain(HttpServletResponse response, String accessToken) {
        // add access token to cookie for every subdomain of gometaworld.io
        saveAccessCookie(response, accessToken);
    }

    public void saveAccessCookie(HttpServletResponse response, String accessToken) {
        Cookie accessCookie = new Cookie(ACCESS_TOKEN_KEY, accessToken);
        accessCookie.setSecure(true);
        response.addCookie(accessCookie);
    }

    public User active(User account) {
        account.setVerified(true);
        account.setVerifyCode(null);
        account.setUpdatedAt(LocalDateTime.now());
        account.setUpdatedBy(account.getId());
        return this.save(account);
    }

    public boolean checkVerifyCode(User account, String verifyCode) {
        return account.getVerifyCode().equals(verifyCode);
    }

    public User created(SignupRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);
        return user;
    }

    public void forgotPassword(User account, ForgotPasswordRequest loginFormDto) {
        long time = new Date().getTime();
        String code = generatorRandomToken(13) + "-" + time;
        String link = "http://localhost:8080/service/change-password";
        account.setVerifyCode(code);
//        emailService.forgot(account.getEmail(), link);
        save(account);
    }
}

