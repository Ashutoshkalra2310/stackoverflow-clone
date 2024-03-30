package io.mountblue.stackoverflowclone.controller;

import io.mountblue.stackoverflowclone.entity.Comment;
import io.mountblue.stackoverflowclone.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addQuestionComment")
    public String saveQuestionComment(@ModelAttribute("comment") Comment comment,
                                      @RequestParam("questionId") Long questionId){
        if(comment.getId() != null){
            commentService.updateQuestionComment(comment, questionId);
        } else {
            commentService.saveQuestionComment(comment, questionId);
        }
        return "redirect:/showQuestion";
    }
}
