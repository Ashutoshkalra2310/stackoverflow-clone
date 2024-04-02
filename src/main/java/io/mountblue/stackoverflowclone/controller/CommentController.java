package io.mountblue.stackoverflowclone.controller;

import io.mountblue.stackoverflowclone.entity.Comment;
import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.service.AnswerService;
import io.mountblue.stackoverflowclone.service.CommentService;
import io.mountblue.stackoverflowclone.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public CommentController(CommentService commentService, QuestionService questionService, AnswerService answerService) {
        this.commentService = commentService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping("/addQuestionComment")
    public String saveQuestionComment(@ModelAttribute("Comment") Comment comment,
                                      @RequestParam("questionId") Long questionId){
        if(comment.getId() != null){
            commentService.updateQuestionComment(comment);
        } else {
            commentService.saveQuestionComment(comment, questionId);
        }
        return "redirect:/question/" + questionId;
    }

    @GetMapping("/deleteQuestionComment/{commentId}")
    public String deleteQuestionComment(@PathVariable("commentId") Long commentId){
        Comment comment = commentService.findById(commentId);
        Question question = questionService.findById(comment.getQuestion().getId());
        commentService.deleteComment(comment);
        return "redirect:/question/" + question.getId();
    }
}
