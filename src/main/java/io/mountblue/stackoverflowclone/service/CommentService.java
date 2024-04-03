package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Comment;

public interface CommentService {
    void saveQuestionComment(Comment comment, Long questionId);
    void updateQuestionComment(Comment comment);
    void deleteComment(Comment comment);
    Comment findById(Long commentId);
}
