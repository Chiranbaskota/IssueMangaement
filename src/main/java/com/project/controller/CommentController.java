package com.project.controller;

import com.project.dto.comment.CommentRequest;
import com.project.dto.comment.CommentResponse;
import com.project.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Comments", description = "Comment management endpoints")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    @Operation(summary = "Add a comment to a post")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest commentRequest,
            Authentication authentication) {
        CommentResponse response = commentService.addComment(postId, commentRequest, authentication.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comments")
    @Operation(summary = "Get all comments for a post")
    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(
            @PathVariable Long postId,
            Authentication authentication) {
        List<CommentResponse> responses = commentService.getCommentsByPostId(postId, authentication.getName());
        return ResponseEntity.ok(responses);
    }
}
