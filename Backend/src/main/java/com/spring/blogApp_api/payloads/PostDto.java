package com.spring.blogApp_api.payloads;

import com.spring.blogApp_api.entities.Category;
import com.spring.blogApp_api.entities.Comment;
import com.spring.blogApp_api.entities.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Integer postId;

    @NotEmpty
    @Size(min = 4, max = 50)
    private String title;

    @NotEmpty
    private String content;

    private String imageName;

    private Date addeddate;

    private UserDto user;

    private CategoryDto category;

    private List<CommentDto> comment;

}
