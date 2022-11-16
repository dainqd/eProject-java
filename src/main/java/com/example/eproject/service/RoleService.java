package com.example.eproject.service;

import com.example.eproject.entity.Role;
import com.example.eproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role save(Role srole) {
        return roleRepository.save(srole);
    }
}