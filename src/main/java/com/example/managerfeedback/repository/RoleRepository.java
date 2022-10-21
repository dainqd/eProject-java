package com.example.managerfeedback.repository;


import com.example.managerfeedback.entity.Role;
import com.example.managerfeedback.util.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /*
     * Find role by name
     * @param name
     * return role
     * */
    Optional<Role> findByName(ERole name);
}
