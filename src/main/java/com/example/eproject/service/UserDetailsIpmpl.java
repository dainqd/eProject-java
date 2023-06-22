package com.example.eproject.service;

import com.example.eproject.entity.User;
import com.example.eproject.util.Enums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class UserDetailsIpmpl implements UserDetails {
    public static final long serialVersionUID = 1L;
    private String username;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsIpmpl(String username, String password,
                            Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static com.example.eproject.service.UserDetailsIpmpl build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority =
                new SimpleGrantedAuthority(user.getRole() == Enums.Role.ADMIN ? "ADMIN" : "USER");
        authorities.add(simpleGrantedAuthority);

        return new com.example.eproject.service.UserDetailsIpmpl(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        com.example.eproject.service.UserDetailsIpmpl user = (com.example.eproject.service.UserDetailsIpmpl) o;
        return Objects.equals(username, user.getUsername());
    }
}
