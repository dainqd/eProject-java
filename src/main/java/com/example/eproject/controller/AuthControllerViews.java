package com.example.eproject.controller;

import com.example.eproject.dto.reponse.JwtResponse;
import com.example.eproject.dto.reponse.LoginRequest;
import com.example.eproject.entity.User;
import com.example.eproject.service.EmailService;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.service.UserDetailsIpmpl;
import com.example.eproject.service.UserDetailsServiceImpl;
import com.example.eproject.util.Enums;
import com.example.eproject.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("service")
@RequiredArgsConstructor
@Slf4j
public class AuthControllerViews {
    final EmailService usernameService;
    final UserDetailsServiceImpl userDetailsService;
    final MessageResourceService messageResourceService;
    final HttpServletRequest request;
    final AuthenticationManager authenticationManager;
    final JwtUtils jwtUtils;

    @GetMapping("login")
    public String login(Model model) {
        LoginRequest loginRequest = new LoginRequest();
        model.addAttribute("loginRequest", loginRequest);
        return "auth/login";
    }

    @GetMapping("register")
    public String register(Model model) {
        return "auth/register";
    }

    @GetMapping("forgot-password")
    public String forgotPassword() {
        return "auth/forgot-password";
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
        if (account.getStatus() == Enums.AccountStatus.DEACTIVE) {
            result.rejectValue("username", "400", messageResourceService.getMessage("account.not.verified"));
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

            String jwt = jwtUtils.generateJwtToken(authentication);

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
            System.out.println(jwtResponse.getToken());
            return "redirect:/";
        } else {
            result.rejectValue("password", "400", messageResourceService.getMessage("account.password.incorrect"));
            model.addAttribute("loginRequest", loginRequest);
            return "auth/login";
        }
    }
}
