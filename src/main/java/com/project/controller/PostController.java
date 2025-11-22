package com.project.controller;

import com.project.dto.post.PostRequest;
import com.project.dto.post.PostResponse;
import com.project.dto.post.PostUpdateRequest;
import com.project.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Posts", description = "Post management endpoints")
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(summary = "Create a new post")
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody PostRequest postRequest,
            Authentication authentication) {
        PostResponse response = postService.createPost(postRequest, authentication.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/submit")
    @Operation(summary = "Submit post for approval")
    public ResponseEntity<PostResponse> submitPost(
            @PathVariable Long id,
            Authentication authentication) {
        PostResponse response = postService.submitPost(id, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Approve a post (Admin only)")
    public ResponseEntity<PostResponse> approvePost(@PathVariable Long id) {
        PostResponse response = postService.approvePost(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Reject a post (Admin only)")
    public ResponseEntity<PostResponse> rejectPost(@PathVariable Long id) {
        PostResponse response = postService.rejectPost(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/close")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Close a post (Admin only)")
    public ResponseEntity<PostResponse> closePost(@PathVariable Long id) {
        PostResponse response = postService.closePost(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/assign-update")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Assign update notes to a post (Admin only)")
    public ResponseEntity<PostResponse> assignUpdate(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequest updateRequest) {
        PostResponse response = postService.assignUpdate(id, updateRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get post by ID")
    public ResponseEntity<PostResponse> getPostById(
            @PathVariable Long id,
            Authentication authentication) {
        PostResponse response = postService.getPostById(id, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all posts (Admin only)")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> responses = postService.getAllPosts();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/approved")
    @Operation(summary = "Get all approved posts")
    public ResponseEntity<List<PostResponse>> getApprovedPosts() {
        List<PostResponse> responses = postService.getApprovedPosts();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/posts")
    @Operation(summary = "Get posts by current user")
    public ResponseEntity<List<PostResponse>> getUserPosts(Authentication authentication) {
        List<PostResponse> responses = postService.getPostsByUser(authentication.getName());
        return ResponseEntity.ok(responses);
    }
}
