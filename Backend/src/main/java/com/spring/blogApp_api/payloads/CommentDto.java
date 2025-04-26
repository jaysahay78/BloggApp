package com.spring.blogApp_api.payloads;

import com.spring.blogApp_api.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class CommentDto {

    private int id;

    private Date addeddate;

    @NotBlank
    private String content;

    private UserDto user;
}
