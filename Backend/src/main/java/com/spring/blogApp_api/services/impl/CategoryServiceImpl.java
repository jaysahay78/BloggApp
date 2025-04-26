package com.spring.blogApp_api.services.impl;

import com.spring.blogApp_api.entities.Category;
import com.spring.blogApp_api.exceptions.ResourceNotFoundException;
import com.spring.blogApp_api.payloads.CategoryDto;
import com.spring.blogApp_api.repositories.CategoryRepo;
import com.spring.blogApp_api.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = this.modelMapper.map(categoryDto, Category.class);
        Category addedCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(addedCat, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer CategoryId) {
        Category cat = this.categoryRepo.findById(CategoryId).orElseThrow(() -> new ResourceNotFoundException("Catgory", "CatergoryId", CategoryId));
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedCat, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer CategoryId) {
        Category cat = categoryRepo.findById(CategoryId).orElseThrow(() -> new ResourceNotFoundException("Catgory", "CategoryId", CategoryId));
        this.categoryRepo.delete(cat);
    }

    @Override
    public CategoryDto getCategory(Integer CategoryId) {
        Category cat = categoryRepo.findById(CategoryId).orElseThrow(() -> new ResourceNotFoundException("Catgory", "CategoryId", CategoryId));
        return this.modelMapper.map(cat, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map((category) -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }
}
