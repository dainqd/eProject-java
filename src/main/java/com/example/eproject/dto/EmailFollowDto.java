package com.example.eproject.dto;

import com.example.eproject.entity.EmailFollow;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailFollowDto {
    private long id;
    private String email;
    private Enums.EmailFollowStatus status = Enums.EmailFollowStatus.PENDING;

    public EmailFollowDto(EmailFollow emailFollow) {
        BeanUtils.copyProperties(emailFollow, this);
    }
}
