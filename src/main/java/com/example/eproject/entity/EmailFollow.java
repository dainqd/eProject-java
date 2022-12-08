package com.example.eproject.entity;

import com.example.eproject.dto.EmailFollowDto;
import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "email_follow")
public class EmailFollow extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Email
    private String email;
    @Enumerated(EnumType.STRING)
    private Enums.EmailFollowStatus status = Enums.EmailFollowStatus.PENDING;

    public EmailFollow(EmailFollowDto emailFollowDto) {
        BeanUtils.copyProperties(emailFollowDto, this);
    }
}
