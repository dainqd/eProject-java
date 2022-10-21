package com.example.managerfeedback.restapi.admin;


import com.example.managerfeedback.entity.Role;
import com.example.managerfeedback.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/request/no/roles/create")
public class CreateApi {
    @Autowired
    RoleService roleService;

//    Tạo role trên heroku bằng api: /api/request/no/roles/create/support
    @PostMapping("/support")
    public ResponseEntity<Role> create(@RequestBody Role role){
        return ResponseEntity.ok(roleService.save(role));
    }
}
