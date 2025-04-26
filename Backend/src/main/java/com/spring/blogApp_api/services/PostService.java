package com.spring.blogApp_api.services;

import com.spring.blogApp_api.entities.Post;
import com.spring.blogApp_api.payloads.PostDto;
import com.spring.blogApp_api.payloads.PostResponse;

import java.util.List;

public interface PostService {

    //create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto, Integer postId);

    //delete
    void deletePost(Integer postId);

    //get
    PostDto getPostById(Integer postId);

    //get all posts
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get all by category
    List<PostDto> getPostByCategory(Integer categoryId);

    //get all by user
    List<PostDto> getPostByUser(Integer userId);

    //search posts
    List<PostDto> SearchPosts(String keyword);
}
