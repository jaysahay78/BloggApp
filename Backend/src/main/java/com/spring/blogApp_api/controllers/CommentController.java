package com.spring.blogApp_api.controllers;

import com.spring.blogApp_api.payloads.ApiResponse;
import com.spring.blogApp_api.payloads.CommentDto;
import com.spring.blogApp_api.services.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@Tag(name = "Comments", description = "Comments on posts APIs")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("posts/{postId}/user/{userId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer postId,
            @PathVariable Integer userId) {
        CommentDto savedComment = this.commentService.createComment(commentDto, postId, userId);
        return new ResponseEntity<>(savedComment, HttpStatus.OK);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted successfully", true), HttpStatus.OK);
    }
}
