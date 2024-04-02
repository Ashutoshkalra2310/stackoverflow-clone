package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Comment;
import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final QuestionService questionService;

    public CommentServiceImpl(CommentRepository commentRepository, QuestionService questionService) {
        this.commentRepository = commentRepository;
        this.questionService = questionService;
    }

    @Override
    public void saveQuestionComment(Comment comment, Long questionId) {
        Question question = questionService.findById(questionId);
        List<Comment> comments = question.getComments();
        comments.add(comment);
        question.setComments(comments);
        questionService.save(question);
    }

    @Override
    public void updateQuestionComment(Comment comment, Long questionId) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(dateTimeFormatter);
        comment.setUpdatedAt(formattedDateTime);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
