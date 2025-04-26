package com.spring.blogApp_api.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateDto {

    @NotEmpty(message = "Old password must not be empty")
    private String oldPassword;

    @NotEmpty(message = "New password must not be empty")
    @Size(min = 6, max = 16, message = "New password must be from 6 to 16 characters")
    private String newPassword;
}

