package com.project.dto.post;

import com.project.enums.PostStatus;
import com.project.enums.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String description;
    private PostType type;
    private PostStatus status;
    private String createdByUsername;
    private String assignedUpdate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
