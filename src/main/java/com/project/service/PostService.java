package com.project.service;

import com.project.dto.post.PostRequest;
import com.project.dto.post.PostResponse;
import com.project.dto.post.PostUpdateRequest;

import java.util.List;

public interface PostService {

    PostResponse createPost(PostRequest postRequest, String username);

    PostResponse submitPost(Long postId, String username);

    PostResponse approvePost(Long postId);

    PostResponse rejectPost(Long postId);

    PostResponse closePost(Long postId);

    PostResponse assignUpdate(Long postId, PostUpdateRequest updateRequest);

    PostResponse getPostById(Long postId, String username);

    List<PostResponse> getAllPosts();

    List<PostResponse> getApprovedPosts();

    List<PostResponse> getPostsByUser(String username);
}
