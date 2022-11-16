package com.example.eproject.config;


import com.example.eproject.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] IGNORE_PATHS = {
            "/", "/css/*", "/img/*", "/favicon.ico",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/csrf",
            "/privacy",
            "/v2/api-docs",
            "/configuration/ui",
            "/api/v1/news/**",
            "/api/v1/category/**",
            "/api/v1/image/**",
            "/api/v1/user/**",
            "/api/v1/auth/**",
            "/api/v1/feedbacks/**",
            "api/v1/course/**"
    };

    private static final String[] USER_PATHS = {
            "/api/feedbacks/**"
    };

    private static final String[] MOD_PATHS = {
            "/mod/api/**",
            "/api/feedbacks/**"
    };

    private static final String[] ADMIN_PATHS = {
            "/admin/api/**",
            "/mod/api/**",
            "/api/feedbacks/**",
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
                .antMatchers(USER_PATHS).hasAuthority("USER")
                .antMatchers(MOD_PATHS).hasAuthority("MODERATOR")
                .antMatchers(ADMIN_PATHS).hasAuthority("ADMIN")
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
