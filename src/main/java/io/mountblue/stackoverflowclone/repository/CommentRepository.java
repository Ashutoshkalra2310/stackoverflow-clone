package io.mountblue.stackoverflowclone.repository;

import io.mountblue.stackoverflowclone.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
