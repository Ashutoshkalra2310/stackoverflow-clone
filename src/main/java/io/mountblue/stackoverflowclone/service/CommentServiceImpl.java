package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Answer;
import io.mountblue.stackoverflowclone.entity.Comment;
import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.User;
import io.mountblue.stackoverflowclone.repository.CommentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    public CommentServiceImpl(CommentRepository commentRepository, QuestionService questionService, AnswerService answerService, UserService userService) {
        this.commentRepository = commentRepository;
        this.questionService = questionService;
        this.answerService = answerService;
        this.userService = userService;
    }

    @Override
    public void saveQuestionComment(Comment comment, Long questionId) {
        Question question = questionService.findById(questionId);
        List<Comment> comments = question.getComments();
        comments.add(comment);
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(dateTimeFormatter);
        comment.setUpdatedAt(formattedDateTime);
        comment.setPublishedAt(formattedDateTime);
        question.setComments(comments);
        comment.setQuestion(question);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        comment.setUser(user);
        questionService.save(question);
    }

    @Override
    public void updateQuestionComment(Comment comment) {
        Comment existingComment = commentRepository.findById(comment.getId()).get();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(dateTimeFormatter);
        existingComment.setUpdatedAt(formattedDateTime);
        existingComment.setComment(comment.getComment());
        commentRepository.save(existingComment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

    public void saveAnswerComment(Comment comment, Long answerId) {
        Answer answer = answerService.findById(answerId);
        List<Comment> comments = answer.getComments();
        comments.add(comment);
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(dateTimeFormatter);
        comment.setUpdatedAt(formattedDateTime);
        answer.setComments(comments);
        comment.setAnswer(answer);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        comment.setUser(user);
        answerService.save(answer);
    }

    public void updateAnswerComment(Comment comment){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(dateTimeFormatter);
        comment.setUpdatedAt(formattedDateTime);
        commentRepository.save(comment);
    }

}
