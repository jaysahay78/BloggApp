package com.spring.blogApp_api.services;

import com.spring.blogApp_api.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer CategoryId);

    //delete
    void deleteCategory(Integer CategoryId);

    //get
    CategoryDto getCategory(Integer CategoryId);

    //get All
    List<CategoryDto> getCategories();
}
