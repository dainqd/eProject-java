package com.example.managerfeedback.service;

import com.example.managerfeedback.entity.Role;
import com.example.managerfeedback.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role save(Role srole){
        return roleRepository.save(srole);
    }
}