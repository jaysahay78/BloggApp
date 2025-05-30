package com.spring.blogApp_api.services.impl;

import com.spring.blogApp_api.entities.Category;
import com.spring.blogApp_api.entities.Post;
import com.spring.blogApp_api.entities.User;
import com.spring.blogApp_api.exceptions.ResourceNotFoundException;
import com.spring.blogApp_api.payloads.PostDto;
import com.spring.blogApp_api.payloads.PostResponse;
import com.spring.blogApp_api.repositories.CategoryRepo;
import com.spring.blogApp_api.repositories.PostRepo;
import com.spring.blogApp_api.repositories.UserRepo;
import com.spring.blogApp_api.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
        post.setTitle(postDto.getTitle());
        post.setImageName(postDto.getImageName());
        post.setAddedDate(new Date());

        Post updatedPost = this.postRepo.save(post);

    return this.modelMapper.map(updatedPost, PostDto.class);}

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = this.postRepo.findAll(p);

        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements((int) pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());


        return postResponse;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "categoryId", categoryId));
        List<Post> posts = this.postRepo.findByCategory(cat);
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "userId", userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post , PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> SearchPosts(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
