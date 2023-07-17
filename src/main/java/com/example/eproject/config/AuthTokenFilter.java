package com.example.eproject.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.eproject.contant.ApplicationConstant;
import com.example.eproject.entity.User;
import com.example.eproject.service.UserDetailsServiceImpl;
import com.example.eproject.util.JwtUtils;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

    /**
     * <div>
     *      Mỗi request của người dùng gọi lên sẽ đi qua filter này.
     *      Hàm doFilterInternal sẽ bỏ qua một số link không cần check quyền được cấu hình từ file SecurityTokenConfig.</div>
     * <br>
     * <div>
     *     Những link còn lại sẽ bóc tách token lấy trong cookie người dùng, verify lại jwt token này để đảm bảo
     *     đây là token do hệ thống mình sinh ra bằng hàm verify trong lớp MetaJwtUtil sử dụng secret key (Hiểu đơn giản thì
     *     một token không được hệ thống sinh ra hoặc đã bị chỉnh sửa sẽ không được verify.)
     * </div>
     * <br>
     * <div>
     *     Token sau khi verify sẽ dùng để lấy thông tin user id và role, phần này để phân quyền tiếp theo.
     *     Thông tin này được lưu vào context của spring và có thể lấy ra ở tất cả các controller khác cùng request.
     * </div>
     */
    @Component
    @Slf4j
    @RequiredArgsConstructor
    public class AuthTokenFilter extends OncePerRequestFilter {

        final UserDetailsServiceImpl accountService;

        @Override
        protected void doFilterInternal(
                HttpServletRequest request,
                HttpServletResponse response,
                FilterChain filterChain
        ) throws ServletException, IOException {
            HttpSession session = request.getSession();
            String token = this.getToken(request);
            if(token == null){
                Cookie cookie = new Cookie("app.base.cookie_name", null);
                cookie.setPath(ApplicationConstant.HOME_PATH);
                response.addCookie(cookie);
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request, response);
                return;
            }
            try {
                DecodedJWT decodedJWT = JwtUtils.getInstance(JwtUtils.jwtSecret).getVerifier().verify(token);
                Optional<User> optionalUser = accountService.findByEmail(decodedJWT.getClaim(JwtUtils.EMAIL_CLAIM_KEY).asString());
                if(optionalUser.isPresent()){
                    User user = optionalUser.get();
                    System.out.println(new Gson().toJson(user));
                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    user.getRoles().forEach(role -> {
                        System.out.println(role.getName().name());
                        authorities.add(new SimpleGrantedAuthority(role.getName().name()));
                    });
                    //check kyc
                    SecurityContextHolder.getContext()
                            .setAuthentication(new UsernamePasswordAuthenticationToken(user, null, authorities));
                    session.setAttribute(ApplicationConstant.CURRENT_LOGGED_IN_KEY, user);
                } else {
                    Cookie cookie = new Cookie("app.base.cookie_name", null);
                    cookie.setPath(ApplicationConstant.HOME_PATH);
                    response.addCookie(cookie);
                }
            } catch (Exception e){
//            Cookie cookie = new Cookie(app.base.cookie_name, null);
//            cookie.setPath(ApplicationConstant.HOME_PATH);
//            response.addCookie(cookie);
//            SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        }

        /**
         * Lấy thông tin token trong tất cả tình huống từ client.
         * Không welcome cách này nhưng tạm thời chưa chuyển vì không kiểm soát được số chức năng liên quan.
         */
        private String getToken(HttpServletRequest request) {
            if (request.getParameter("app.base.cookie_name") != null) {
                return request.getParameter("app.base.cookie_name");
            }
            if (request.getHeader("app.base.cookie_name") != null) {
                return request.getHeader("app.base.cookie_name");
            }
            // Dùng trong api call lên.
            if (request.getHeader(ApplicationConstant.AUTHORIZATION_NAME) != null) {
                // Lấy thông tin token trong header Authentication: Bearer _token.
                return request.getHeader(ApplicationConstant.AUTHORIZATION_NAME)
                        .replaceAll(ApplicationConstant.AUTHORIZATION_TOKEN_TYPE, ApplicationConstant.EMPTY_STRING)
                        .trim();
            }
            if (WebUtils.getCookie(request, "app.base.cookie_name") != null) {
                return Objects.requireNonNull(WebUtils.getCookie(request, "app.base.cookie_name")).getValue();
            }
            return null;
        }

        public String currentToken(HttpServletRequest request){
            return getToken(request);
        }
    }