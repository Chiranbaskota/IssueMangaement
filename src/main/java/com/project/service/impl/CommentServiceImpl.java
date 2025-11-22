package com.project.service.impl;

import com.project.dto.comment.CommentRequest;
import com.project.dto.comment.CommentResponse;
import com.project.entity.Comment;
import com.project.entity.Post;
import com.project.entity.User;
import com.project.enums.PostStatus;
import com.project.enums.RoleType;
import com.project.exception.InvalidOperationException;
import com.project.exception.ResourceNotFoundException;
import com.project.exception.UnauthorizedException;
import com.project.repository.CommentRepository;
import com.project.repository.PostRepository;
import com.project.service.CommentService;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    @Transactional
    public CommentResponse addComment(Long postId, CommentRequest commentRequest, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        User user = userService.findByUsername(username);

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName() == RoleType.ADMIN);

        boolean isOwner = post.getCreatedBy().getId().equals(user.getId());

        if (!isAdmin && !isOwner && post.getStatus() != PostStatus.APPROVED) {
            throw new UnauthorizedException("You can only comment on your own posts or approved posts");
        }

        Comment comment = new Comment();
        comment.setText(commentRequest.getText());
        comment.setPost(post);
        comment.setCreatedBy(user);

        Comment savedComment = commentRepository.save(comment);
        return convertToResponse(savedComment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByPostId(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        User user = userService.findByUsername(username);

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName() == RoleType.ADMIN);

        boolean isOwner = post.getCreatedBy().getId().equals(user.getId());

        if (!isAdmin && !isOwner && post.getStatus() != PostStatus.APPROVED) {
            throw new UnauthorizedException("You don't have permission to view comments on this post");
        }

        return commentRepository.findByPostOrderByCreatedAtDesc(post).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private CommentResponse convertToResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setText(comment.getText());
        response.setPostId(comment.getPost().getId());
        response.setCreatedByUsername(comment.getCreatedBy().getUsername());
        response.setCreatedAt(comment.getCreatedAt());
        return response;
    }
}
