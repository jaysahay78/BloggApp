package com.spring.blogApp_api.services.impl;

import com.spring.blogApp_api.entities.Comment;
import com.spring.blogApp_api.entities.Post;
import com.spring.blogApp_api.entities.User;
import com.spring.blogApp_api.exceptions.ResourceNotFoundException;
import com.spring.blogApp_api.payloads.CommentDto;
import com.spring.blogApp_api.repositories.CommentRepo;
import com.spring.blogApp_api.repositories.PostRepo;
import com.spring.blogApp_api.repositories.UserRepo;
import com.spring.blogApp_api.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        comment.setAddedDate(new Date());
        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
        this.commentRepo.delete(comment);
    }
}
