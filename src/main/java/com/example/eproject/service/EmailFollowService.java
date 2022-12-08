package com.example.eproject.service;

import com.example.eproject.dto.EmailFollowDto;
import com.example.eproject.entity.EmailFollow;
import com.example.eproject.repository.EmailFollowRepository;
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
public class EmailFollowService {
    final EmailFollowRepository emailFollowRepository;
    final MessageResourceService messageResourceService;

    public Page<EmailFollow> getAll(Pageable pageable) {
        return emailFollowRepository.findAll(pageable);
    }

    public Page<EmailFollow> getAllByStatus(Enums.EmailFollowStatus status, Pageable pageable) {
        return emailFollowRepository.findAllByStatus(status, pageable);
    }

    public Optional<EmailFollow> getDetail(long id) {
        return emailFollowRepository.findById(id);
    }

    public Optional<EmailFollow> getDetailByIdAndStatus(long id, Enums.EmailFollowStatus status) {
        return emailFollowRepository.findByIdAndStatus(id, status);
    }

    public EmailFollow create(EmailFollowDto emailFollowDto, long adminID) {
        EmailFollow emailFollow = new EmailFollow();

        BeanUtils.copyProperties(emailFollowDto, emailFollow);
        emailFollow.setCreatedAt(LocalDateTime.now());
        emailFollow.setCreatedBy(adminID);

        return emailFollowRepository.save(emailFollow);
    }

    public EmailFollow save(EmailFollowDto emailFollowDto) {
        EmailFollow emailFollow = new EmailFollow();

        BeanUtils.copyProperties(emailFollowDto, emailFollow);
        emailFollow.setCreatedAt(LocalDateTime.now());

        return emailFollowRepository.save(emailFollow);
    }

    public EmailFollow update(EmailFollowDto emailFollowDto, long adminID) {
        Optional<EmailFollow> optionalEmailFollow = emailFollowRepository.findById(emailFollowDto.getId());
        if (!optionalEmailFollow.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        EmailFollow emailFollow = optionalEmailFollow.get();

        BeanUtils.copyProperties(emailFollowDto, emailFollow);
        emailFollow.setUpdatedAt(LocalDateTime.now());
        emailFollow.setUpdatedBy(adminID);
        return emailFollowRepository.save(emailFollow);
    }

    public void delete(long id, long adminID) {
        Optional<EmailFollow> optionalEmailFollow = emailFollowRepository.findById(id);
        if (!optionalEmailFollow.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }

        EmailFollow emailFollow = optionalEmailFollow.get();
        emailFollow.setStatus(Enums.EmailFollowStatus.DELETED);
        emailFollow.setDeletedAt(LocalDateTime.now());
        emailFollow.setDeletedBy(adminID);
        emailFollowRepository.save(emailFollow);
    }
}
