package com.example.eproject.controller;

import com.example.eproject.dto.reponse.JwtResponse;
import com.example.eproject.dto.request.ForgotPasswordRequest;
import com.example.eproject.dto.request.LoginRequest;
import com.example.eproject.dto.request.SignupRequest;
import com.example.eproject.entity.User;
import com.example.eproject.service.EmailService;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.service.UserDetailsIpmpl;
import com.example.eproject.service.UserDetailsServiceImpl;
import com.example.eproject.util.Enums;
import com.example.eproject.util.JwtUtils;
import com.example.eproject.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    final EmailService usernameService;
    final UserDetailsServiceImpl userDetailsService;
    final MessageResourceService messageResourceService;
    final HttpServletRequest request;
    final AuthenticationManager authenticationManager;
     JwtUtils jwtUtils;
    final EmailService emailService;

    @GetMapping("login")
    public String login(Model model) {
        LoginRequest loginRequest = new LoginRequest();
        model.addAttribute("loginRequest", loginRequest);
        return "auth/login";
    }

    @GetMapping("register")
    public String register(Model model) {
        SignupRequest signupRequest = new SignupRequest();
        model.addAttribute("signupRequest", signupRequest);
        return "auth/register";
    }

    @GetMapping("forgot-password")
    public String forgotPassword(Model model) {
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        model.addAttribute("forgotPasswordRequest", forgotPasswordRequest);
        return "auth/forgot-password";
    }

    @GetMapping("register-verify")
    public String verify(Model model) {
        SignupRequest signupRequest = new SignupRequest();
        model.addAttribute("signupRequest", signupRequest);
        return "auth/register-verify";
    }

    @GetMapping("change-password")
    public String changePassword(Model model) {
        SignupRequest signupRequest = new SignupRequest();
        model.addAttribute("signupRequest", signupRequest);
        return "auth/change-password";
    }

    @PostMapping("login")
    public String processServiceLogin(
            @Valid @ModelAttribute LoginRequest loginRequest,
            BindingResult result,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response) {
        Optional<User> optionalUser = userDetailsService.findByUsername(loginRequest.getUsername());
        if (!optionalUser.isPresent()) {
            result.rejectValue("username", "400", messageResourceService.getMessage("account.not.found"));
            model.addAttribute("loginRequest", loginRequest);
            return "auth/login";
        }
        User account = optionalUser.get();
        if (!account.isVerified()) {
            result.rejectValue("username", "400", messageResourceService.getMessage("account.not.verified"));
            model.addAttribute("loginRequest", loginRequest);
            return "auth/login";
        }
        if (account.getStatus() == Enums.AccountStatus.DEACTIVE) {
            result.rejectValue("username", "400", messageResourceService.getMessage("account.not.active"));
            model.addAttribute("loginRequest", loginRequest);
            return "auth/login";
        }
        if (account.getStatus() == Enums.AccountStatus.BLOCKED) {
            result.rejectValue("username", "400", messageResourceService.getMessage("account.banned"));
            model.addAttribute("loginRequest", loginRequest);
            return "auth/login";
        }
        if (account.getStatus() == Enums.AccountStatus.DELETED) {
            result.rejectValue("username", "400", messageResourceService.getMessage("account.deleted"));
            model.addAttribute("loginRequest", loginRequest);
            return "auth/login";
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
            JwtResponse jwtResponse = new JwtResponse("success",
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
                    roles);
            return "redirect:/";
        } else {
            result.rejectValue("password", "400", messageResourceService.getMessage("account.password.incorrect"));
            model.addAttribute("loginRequest", loginRequest);
            return "auth/login";
        }
    }

    @PostMapping("register")
    public String register(
            @Valid @ModelAttribute SignupRequest signupRequest,
            BindingResult result,
            Model model, HttpSession session) {
        Optional<User> optionalUser = userDetailsService.findByUsername(signupRequest.getUsername());
        if (optionalUser.isPresent()) {
            result.rejectValue("username", "400", messageResourceService.getMessage("account.username.exist"));
            model.addAttribute("signupRequest", signupRequest);
            return "auth/register";
        }
        Optional<User> userOptional = userDetailsService.findByEmail(signupRequest.getEmail());
        if (userOptional.isPresent()) {
            result.rejectValue("email", "400", messageResourceService.getMessage("account.email.exist"));
            model.addAttribute("signupRequest", signupRequest);
            return "auth/register";
        }
        if (!Objects.equals(signupRequest.getPassword(), signupRequest.getPasswordConfirm())) {
            result.rejectValue("password", "400", messageResourceService.getMessage("account.password.incorrect"));
            model.addAttribute("signupRequest", signupRequest);
            return "auth/register";
        }
        if (signupRequest.getPassword().length() < 6 || signupRequest.getPassword() == null || Objects.equals(signupRequest.getPassword(), "")) {
            result.rejectValue("password", "400", messageResourceService.getMessage("account.password.invalid"));
            model.addAttribute("signupRequest", signupRequest);
            return "auth/register";
        }
        // Xử lý create account
        String verifyCode = Utils.generatorVerifyCode(6);
        userDetailsService.create(signupRequest, verifyCode);
        emailService.userRegisterMail(signupRequest.getEmail(), verifyCode);
        model.addAttribute("signupRequest", signupRequest);
        return "redirect:/service/register-verify";
    }

    @PostMapping("register-verify")
    public String registerVerify(
            @Valid @ModelAttribute SignupRequest signupRequest, BindingResult result, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        Optional<User> optionalUser = userDetailsService.findByVerifyCode(signupRequest.getVerifyCode());
        model.addAttribute("isSuccess", true);
        model.addAttribute("isVerify", false);
        if (signupRequest.getVerifyCode().isEmpty()) {
            result.rejectValue("verifyCode", "400", messageResourceService.getMessage("account.verify.empty"));
            model.addAttribute("signupRequest", signupRequest);
            return "auth/register-verify";
        }
        if (!optionalUser.isPresent()) {
            result.rejectValue("verifyCode", "400", messageResourceService.getMessage("account.not.found"));
            model.addAttribute("signupRequest", signupRequest);
            return "auth/register-verify";
        }
        User account = optionalUser.get();
        if (account.isVerified()) {
            result.rejectValue("verifyCode", "400", messageResourceService.getMessage("account.verified"));
            model.addAttribute("signupRequest", signupRequest);
            return "auth/register-verify";
        }
        if (!userDetailsService.checkVerifyCode(account, signupRequest.getVerifyCode())) {
            result.rejectValue("verifyCode", "400", messageResourceService.getMessage("account.verifycode.incorrect"));
            model.addAttribute("signupRequest", signupRequest);
            return "auth/register-verify";
        }
        model.addAttribute("account", account);
        userDetailsService.active(account);
        model.addAttribute("signupRequest", signupRequest);
//        model.addAttribute("messageVerifySuccess", "Verify Success");
        return "redirect:/";
    }

    @PostMapping("forgot-password")
    public String forgotPassword(
            @Valid @ModelAttribute ForgotPasswordRequest forgotPasswordRequest,
            BindingResult result,
            Model model) {
        Optional<User> optionalUser = userDetailsService.findByEmail(forgotPasswordRequest.getEmail());
        if (!optionalUser.isPresent()) {
            result.rejectValue("email", "400", messageResourceService.getMessage("account.not.found"));
            model.addAttribute("forgotPasswordRequest", forgotPasswordRequest);
            return "auth/forgot-password";
        }
        User account = optionalUser.get();
        if (!account.isVerified()) {
            result.rejectValue("email", "400", messageResourceService.getMessage("account.not.verified"));
            model.addAttribute("forgotPasswordRequest", forgotPasswordRequest);
            return "auth/forgot-password";
        }
        if (account.getStatus() == Enums.AccountStatus.DEACTIVE) {
            result.rejectValue("email", "400", messageResourceService.getMessage("account.not.active"));
            model.addAttribute("forgotPasswordRequest", forgotPasswordRequest);
            return "auth/forgot-password";
        }
        if (account.getStatus() == Enums.AccountStatus.BLOCKED) {
            result.rejectValue("email", "400", messageResourceService.getMessage("account.banned"));
            model.addAttribute("forgotPasswordRequest", forgotPasswordRequest);
            return "auth/forgot-password";
        }
        if (account.getStatus() == Enums.AccountStatus.DELETED) {
            result.rejectValue("email", "400", messageResourceService.getMessage("account.deleted"));
            model.addAttribute("forgotPasswordRequest", forgotPasswordRequest);
            return "auth/forgot-password";
        }
        // Xử lý create account
        userDetailsService.forgotPassword(account, forgotPasswordRequest);
        model.addAttribute("forgotPasswordRequest", forgotPasswordRequest);
        return "auth/forgot-password";
    }
}
