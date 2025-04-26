package com.spring.blogApp_api.repositories;

import com.spring.blogApp_api.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
