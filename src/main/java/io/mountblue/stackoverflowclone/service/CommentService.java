package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Comment;

public interface CommentService {
    void saveQuestionComment(Comment comment, Long questionId);
    void updateComment(Comment comment);
    void deleteComment(Comment comment);
    Comment findById(Long commentId);
    public void saveAnswerComment(Comment comment, Long answerId);
    public void updateAnswerComment(Comment comment);
}
