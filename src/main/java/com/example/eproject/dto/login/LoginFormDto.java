package com.example.eproject.dto.login;

import com.example.eproject.service.MessageResourceService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;

@Getter
@Setter
@AllArgsConstructor
public class LoginFormDto {
    //    @NotEmpty(message = "{account.email.required}")
    private String email;
    //    @NotEmpty(message = "{account.password.required}")
    private String password;
    private String verifyCode;

    public LoginFormDto() {
        this.email = "";
        this.password = "";
        this.verifyCode = "";
    }

    public boolean checkEmail(BindingResult result, MessageResourceService mrs) {
        if (this.email == null || this.email.isEmpty()) {
            result.rejectValue("email", "400", mrs.getMessage("account.email.required"));
            return true;
        }
        return false;
    }

}
