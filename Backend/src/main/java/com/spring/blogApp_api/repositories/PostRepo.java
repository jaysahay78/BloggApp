package com.spring.blogApp_api.repositories;

import com.spring.blogApp_api.entities.Category;
import com.spring.blogApp_api.entities.Post;
import com.spring.blogApp_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);
}
