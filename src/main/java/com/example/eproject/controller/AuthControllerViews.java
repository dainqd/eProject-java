package com.example.eproject.controller;

import com.example.eproject.dto.reponse.LoginRequest;
import com.example.eproject.entity.User;
import com.example.eproject.service.EmailService;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.service.UserDetailsServiceImpl;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

import static com.example.eproject.util.Utils.isValidEmail;

@Controller
@RequestMapping("service")
@RequiredArgsConstructor
@Slf4j
public class AuthControllerViews {
    final EmailService usernameService;
    final UserDetailsServiceImpl userDetailsService;
    final MessageResourceService mrs;
    final HttpServletRequest request;

    @GetMapping("login")
    public String login(Model model,
                        HttpSession session) {
        LoginRequest loginRequest = new LoginRequest();
        model.addAttribute("loginRequest", loginRequest);
        return "auth/login";
    }

    @GetMapping("register")
    public String register() {
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
            result.rejectValue("username", "400", mrs.getMessage("account.not.found"));
            model.addAttribute("loginRequest", loginRequest);
            return "views/login";
        }
        User account = optionalUser.get();
        if (account.getStatus() == Enums.AccountStatus.DEACTIVE) {
            result.rejectValue("username", "400", mrs.getMessage("account.not.verified"));
            model.addAttribute("loginRequest", loginRequest);
            return "views/login";
        }
        if (account.getStatus() == Enums.AccountStatus.BLOCKED) {
            result.rejectValue("username", "400", mrs.getMessage("account.banned"));
            model.addAttribute("loginRequest", loginRequest);
            return "views/login";
        }
        if (account.getStatus() == Enums.AccountStatus.DELETED) {
            result.rejectValue("username", "400", mrs.getMessage("account.deleted"));
            model.addAttribute("loginRequest", loginRequest);
            return "views/login";
        }
        // Xử lý check mật khẩu, add login history, update last login.
        boolean isMatch = userDetailsService.checkPasswordMatch(loginRequest.getPassword(), account);
        if (isMatch) {
            return "/";
        } else {
            result.rejectValue("password", "400", mrs.getMessage("account.password.incorrect"));
            model.addAttribute("loginRequest", loginRequest);
            return "views/login";
        }
    }
}
