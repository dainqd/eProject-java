//package com.example.eproject.config;
//
//import com.example.eproject.dto.CredentialDto;
//import com.example.eproject.dto.request.LoginRequest;
//import com.example.eproject.entity.User;
//import com.example.eproject.repository.UserRepository;
//import com.example.eproject.util.JwtUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import com.google.gson.Gson;
//import org.springframework.web.server.ResponseStatusException;
//
//@Slf4j
//@RequiredArgsConstructor
//public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    final AuthenticationManager authenticationManager;
//
//    final UserRepository userRepository;
//    final JwtUtils jwtUtils;
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
//            String jsonData = request.getReader().lines().collect(Collectors.joining());
//            Gson gson = new Gson();
//            LoginRequest accountLoginDto = gson.fromJson(jsonData, LoginRequest.class);
//            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
//                    accountLoginDto.getUsername(), accountLoginDto.getPassword());
//            return authenticationManager.authenticate(userToken);
//        } catch (IOException exception) {
//            return null;
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
//        Optional<User> accountOptional = userRepository.findByUsername(user.getUsername());
//        if (!accountOptional.isPresent()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account not found");
//        }
//        User account = accountOptional.get();
//        String accessToken = jwtUtils.generateToken(user.getUsername(),
//                user.getAuthorities().iterator().next().getAuthority(),
//                request.getRequestURL().toString(),
//                jwtUtils.ONE_DAY * 7);
//        String refreshToken = jwtUtils.generateToken(user.getUsername(),
//                user.getAuthorities().iterator().next().getAuthority(),
//                request.getRequestURL().toString(),
//                jwtUtils.ONE_DAY * 14);
//        CredentialDto credential = new CredentialDto(account.getId(), account.getUsername(), accessToken, refreshToken,
//                jwtUtils.ONE_DAY * 7, "basic_information");
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        Gson gson = new Gson();
//        response.getWriter().println(gson.toJson(credential));
//        System.out.println("Succesc login");
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        HashMap<String, String> errors = new HashMap<>();
//        errors.put("message", "Invalid information");
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        Gson gson = new Gson();
//        response.getWriter().println(gson.toJson(errors));
//    }
//}