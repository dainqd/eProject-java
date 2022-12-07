package com.example.eproject.service;

import com.example.eproject.dto.ManagerDto;
import com.example.eproject.entity.Manager;
import com.example.eproject.repository.ManagerRepository;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    final ManagerRepository managerRepository;

    public Page<Manager> findAll(Pageable pageable) {
        return managerRepository.findAll(pageable);
    }

    public Page<Manager> findAllByStatus(Enums.ManagerStatus status, Pageable pageable) {
        return managerRepository.findAllByStatus(status, pageable);
    }

    public Optional<Manager> findById(long id) {
        return managerRepository.findById(id);
    }

    public Optional<Manager> findByIdAndStatus(long id, Enums.ManagerStatus status) {
        return managerRepository.findByIdAndStatus(id, status);
    }

    public Manager create(ManagerDto managerDto, long adminID) {
        Manager manager = new Manager();

        BeanUtils.copyProperties(managerDto, manager);

        manager.setCreatedAt(LocalDateTime.now());
        manager.setCreatedBy(adminID);
        return managerRepository.save(manager);
    }

    public Manager save(Manager manager) {
        return managerRepository.save(manager);
    }

    public void delete(Manager manager, long adminID) {
        manager.setStatus(Enums.ManagerStatus.DELETED);
        manager.setDeletedAt(LocalDateTime.now());
        manager.setDeletedBy(adminID);
        managerRepository.save(manager);
    }

    public Manager update(ManagerDto managerDto, long adminID) {
        Optional<Manager> optionalManager = managerRepository.findById(managerDto.getId());
        if (!optionalManager.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR");
        }
        Manager manager = optionalManager.get();

        BeanUtils.copyProperties(managerDto, manager);

        manager.setUpdatedAt(LocalDateTime.now());
        manager.setUpdatedBy(adminID);
        return managerRepository.save(manager);
    }
}
