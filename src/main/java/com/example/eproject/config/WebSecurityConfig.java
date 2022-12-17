package com.example.eproject.config;

import com.example.eproject.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] IGNORE_PATHS = {
            "/", "/css/**", "/img/**", "/favicon.ico",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/csrf",
            "/privacy",
            "/v2/api-docs",
            "/configuration/ui",
            "/js/**",
            "/fonts/**",
            "/lib/**",
            "/scss/**",
            "/icons/**",
            "/service/login",
            "/service/logout",
            "/service/forgot-password",
            "/service/register",
            "/service/register-verify",
            "/service/change-password",
            "/service/change-email",
            "/assets/**",
            "/auth/**",
            "/element/**",
            "/error/**",
            "/v1/**",
            "/views/**",
            "/layout/**",
            "/include/**",
            "/templates/**",
            "/home/**",
            "/admissions/**",
            "/courses/**",
            "/events/**",
            "/blog-home/**",
            "/api/v1/news/**",
            "/api/v1/category/**",
            "/api/v1/image/**",
            "/api/v1/user/**",
            "/api/v1/auth/**",
            "/api/v1/feedbacks/**",
            "/api/v1/course/**",
            "/api/v1/events/**",
            "/api/v1/faculty/**",
            "/api/v1/classroom/**",
            "/api/v1/mark-report/**",
            "/api/v1/attendance-report/**",
    };

    private static final String[] STUDENT_PATHS = {
            "/student/api/**",
    };

    private static final String[] TEACHER_PATHS = {
            "/teacher/api/**",
    };

    private static final String[] USER_PATHS = {
            "/user/api/**",
    };

    private static final String[] MOD_PATHS = {
            "/mod/api/**",
    };

    private static final String[] ADMIN_PATHS = {
            "/admin/api/**",
            "/mod/api/**",
            "/api/request/no/roles/create/support"
    };

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public com.example.eproject.config.AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSender() {
            @Override
            public void send(SimpleMailMessage simpleMessage) throws MailException {

            }

            @Override
            public void send(SimpleMailMessage... simpleMessages) throws MailException {

            }

            @Override
            public MimeMessage createMimeMessage() {
                return null;
            }

            @Override
            public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
                return null;
            }

            @Override
            public void send(MimeMessage mimeMessage) throws MailException {

            }

            @Override
            public void send(MimeMessage... mimeMessages) throws MailException {

            }

            @Override
            public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {

            }

            @Override
            public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {

            }
        };
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers(IGNORE_PATHS).permitAll()
                .antMatchers(STUDENT_PATHS).hasAuthority("STUDENT")
                .antMatchers(TEACHER_PATHS).hasAuthority("TEACHER")
                .antMatchers(USER_PATHS).hasAuthority("USER")
                .antMatchers(MOD_PATHS).hasAuthority("MODERATOR")
                .antMatchers(ADMIN_PATHS).hasAuthority("ADMIN")
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
