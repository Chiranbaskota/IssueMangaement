package com.project.repository;

import com.project.entity.Post;
import com.project.entity.User;
import com.project.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByStatus(PostStatus status);

    List<Post> findByCreatedBy(User user);

    List<Post> findByStatusIn(List<PostStatus> statuses);
}
