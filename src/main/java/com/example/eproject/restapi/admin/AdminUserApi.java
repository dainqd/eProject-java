package com.example.eproject.restapi.admin;

import com.example.eproject.dto.UserDto;
import com.example.eproject.entity.User;
import com.example.eproject.repository.RoleRepository;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("admin/api/user")
public class AdminUserApi {
    @Autowired
    UserDetailsServiceImpl userDetailsServiceimpl;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    final MessageResourceService messageResourceService;

    @GetMapping()
    public ResponseEntity<List<User>> getList() {
        return ResponseEntity.ok(userDetailsServiceimpl.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Long id) {
        Optional<User> optionalUser = userDetailsServiceimpl.findById(id);
        if (!optionalUser.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalUser.get());
    }

    @PostMapping()
    public ResponseEntity<User> create(@RequestBody UserDto user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return ResponseEntity.ok(userDetailsServiceimpl.create(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> optionalUser = userDetailsServiceimpl.findById(id);
        if ((!optionalUser.isPresent())) {
            ResponseEntity.badRequest().build();
        }
        User existUser = optionalUser.get();

        existUser.setAvt(user.getAvt());
        existUser.setFirstName(user.getFirstName());
        existUser.setLastName(user.getLastName());
        existUser.setUsername(user.getUsername());
        existUser.setEmail(user.getEmail());
        existUser.setPhoneNumber(user.getPhoneNumber());
        existUser.setBirthday(user.getBirthday());
        existUser.setGender(user.getGender());
        existUser.setAddress(user.getAddress());
        existUser.setPassword(encoder.encode(user.getPassword()));
        existUser.setRole(user.getRole());
        return ResponseEntity.ok(userDetailsServiceimpl.save(existUser));
    }

    @PutMapping("/{id}/{keyword}")
    public ResponseEntity<User> updated(@PathVariable Long id, @PathVariable String keyword, @RequestBody User user) {
        Optional<User> optionalUser = userDetailsServiceimpl.findById(id);
        if ((!optionalUser.isPresent())) {
            ResponseEntity.badRequest().build();
        }
        User existUser = optionalUser.get();

        if (keyword.equals("avt")) {
            existUser.setAvt(user.getAvt());
        } else if (keyword.equals("firstName")) {
            existUser.setFirstName(user.getFirstName());
        } else if (keyword.equals("lastName")) {
            existUser.setLastName(user.getLastName());
        } else if (keyword.equals("username")) {
            existUser.setUsername(user.getUsername());
        } else if (keyword.equals("email")) {
            existUser.setEmail(user.getEmail());
        } else if (keyword.equals("phoneNumber")) {
            existUser.setPhoneNumber(user.getPhoneNumber());
        } else if (keyword.equals("birthday")) {
            existUser.setBirthday(user.getBirthday());
        } else if (keyword.equals("gender")) {
            existUser.setGender(user.getGender());
        } else if (keyword.equals("address")) {
            existUser.setAddress(user.getAddress());
        } else if (keyword.equals("password")) {
            existUser.setPassword(encoder.encode(user.getPassword()));
        } else if (keyword.equals("roles")) {
            existUser.setRole(user.getRole());
        } else {
            ResponseEntity.badRequest();
            new RuntimeException("Error: keyword not true");
        }
        return ResponseEntity.ok(userDetailsServiceimpl.save(existUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if ((!userDetailsServiceimpl.findById(id).isPresent())) {
            ResponseEntity.badRequest().build();
        }
        userDetailsServiceimpl.deleteById(id);
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }
}
