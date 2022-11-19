package com.example.eproject.controller;

import com.example.eproject.dto.login.LoginFormDto;
import com.example.eproject.dto.reponse.JwtResponse;
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
    final EmailService emailService;
    final UserDetailsServiceImpl userDetailsService;
    final MessageResourceService mrs;
    final HttpServletRequest request;
    @GetMapping("login")
    public String login(Model model,
                        HttpSession session) {
        LoginFormDto loginFormDto = new LoginFormDto();
        model.addAttribute("loginFormDto", loginFormDto);
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

//    @PostMapping("login")
//    public String processServiceLogin(
//            @Valid @ModelAttribute LoginFormDto loginFormDto,
//            BindingResult result,
//            Model model,
//            HttpServletRequest request,
//            HttpServletResponse response) {
//        if (!isValidEmail(loginFormDto.getEmail())) {
//            result.rejectValue("email", "400", mrs.getMessage("login.email.invalid"));
//            model.addAttribute("loginFormDto", loginFormDto);
//            return "views/login";
//        }
//
//        Optional<User> optionalUser = userDetailsService.findByEmail(loginFormDto.getEmail());
//        if (!optionalUser.isPresent()) {
//            result.rejectValue("email", "400", mrs.getMessage("account.notfound"));
//            model.addAttribute("loginFormDto", loginFormDto);
//            return "views/login";
//        }
//        User account = optionalUser.get();
////        if (!account.isVerified()) {
////            result.rejectValue("email", "400", mrs.getMessage("account.notverified"));
////            model.addAttribute("loginFormDto", loginFormDto);
////            return "views/login";
////        }
//        if (account.getStatus() == Enums.AccountStatus.DEACTIVE || account.getStatus() == Enums.AccountStatus.BLOCKED) {
//            result.rejectValue("email", "400", mrs.getMessage("account.banned"));
//            model.addAttribute("loginFormDto", loginFormDto);
//            return "views/login";
//        }
//        if (account.getStatus() == Enums.AccountStatus.DELETED) {
//            result.rejectValue("email", "400", mrs.getMessage("account.deleted"));
//            model.addAttribute("loginFormDto", loginFormDto);
//            return "views/login";
//        }
//        // Xử lý check mật khẩu, add login history, update last login.
////        boolean isMatch = userDetailsService.checkPasswordMatch(loginFormDto.getPassword(), account);
////        if (isMatch) {
////            return "/";
////        } else {
////            result.rejectValue("password", "400", mrs.getMessage("account.password.incorrect"));
////            model.addAttribute("loginFormDto", loginFormDto);
////            return "views/login";
////        }
//        return "/";
//    }
}
