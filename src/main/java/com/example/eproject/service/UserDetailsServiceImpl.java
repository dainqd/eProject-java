package com.example.eproject.service;

import com.example.eproject.dto.UserDto;
import com.example.eproject.dto.request.ForgotPasswordRequest;
import com.example.eproject.dto.request.SignupRequest;
import com.example.eproject.entity.*;
import com.example.eproject.repository.RoleRepository;
import com.example.eproject.repository.UserRepository;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    final EmailService emailService;

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
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(1L);
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
        Optional<User> optionalAccount = userRepository.findByUsername(username);
        if(!optionalAccount.isPresent()){
            throw  new UsernameNotFoundException("Username is not found");
        }
        User account = optionalAccount.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority =
                new SimpleGrantedAuthority(account.getRole() == Enums.Role.ADMIN ? "ADMIN" : "USER");
        authorities.add(simpleGrantedAuthority);
        return new UserDetailsIpmpl(account.getUsername(),account.getPassword(), authorities);
    }

    public void createReferralCode(User account) {
        String referral = decimalToHex(Long.parseLong(generatorVerifyCode(8))) + decimalToHex(account.getId());
        account.setReferralCode(referral);
        userRepository.save(account);
    }

    public User create(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(1L);
        return userRepository.save(user);
    }

    public User signup(SignupRequest request, String verifyCode) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setVerifyCode(verifyCode);
        user.setRole(Enums.Role.USER);
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

    public User active(User user) {
        user.setVerified(true);
        user.setVerifyCode(null);
        return this.save(user);
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

    public void forgotPassword(User user, ForgotPasswordRequest forgotPasswordRequest) {
        long time = new Date().getTime();
        String code = generatorRandomToken(13) + "-" + time;
        String link = "http://localhost:8080/service/change-password";
        user.setVerifyCode(code);
        emailService.forgot(user.getEmail(), link);
        save(user);
    }
}

