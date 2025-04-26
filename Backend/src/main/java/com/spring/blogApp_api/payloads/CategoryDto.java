package com.spring.blogApp_api.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private int categoryId;

    @NotBlank
    @Size(min = 4, message = "minimum size of title is 4")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10, max = 200, message = "description must be from 10 to 100 characters")
    private String categoryDescription;
}
