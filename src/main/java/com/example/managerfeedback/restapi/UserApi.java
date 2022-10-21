package com.example.managerfeedback.restapi;

import com.example.managerfeedback.entity.User;
import com.example.managerfeedback.repository.RoleRepository;
import com.example.managerfeedback.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserApi {
    @Autowired
    UserDetailsServiceImpl userDetailsServiceimpl;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/views/list")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<User>> getList(){
        return ResponseEntity.ok(userDetailsServiceimpl.findAll());
    }

    @GetMapping("/views/list/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getDetail(@PathVariable Long id) {
        Optional<User> optionalUser = userDetailsServiceimpl.findById(id);
        if (!optionalUser.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalUser.get());
    }
}
