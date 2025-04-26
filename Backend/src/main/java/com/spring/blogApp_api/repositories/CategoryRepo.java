package com.spring.blogApp_api.repositories;

import com.spring.blogApp_api.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
