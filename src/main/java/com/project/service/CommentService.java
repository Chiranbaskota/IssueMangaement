package com.project.service;

import com.project.dto.comment.CommentRequest;
import com.project.dto.comment.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse addComment(Long postId, CommentRequest commentRequest, String username);

    List<CommentResponse> getCommentsByPostId(Long postId, String username);
}
