package com.spring.blogApp_api.payloads;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDto {
        private int id;

        @NotEmpty
        @Size(min = 4, message = "username must be at least 4 characters")
        private String name;

        @Email(message = "Email address is not valid")
        private String email;

        @NotEmpty
        private String about;

}
