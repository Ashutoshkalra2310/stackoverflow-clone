package io.mountblue.stackoverflowclone.controller;

import io.mountblue.stackoverflowclone.entity.Answer;
import io.mountblue.stackoverflowclone.entity.Comment;
import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.service.AnswerService;
import io.mountblue.stackoverflowclone.service.CommentService;
import io.mountblue.stackoverflowclone.service.QuestionService;
import io.mountblue.stackoverflowclone.service.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final StorageService storageService;

    public CommentController(CommentService commentService, QuestionService questionService, AnswerService answerService, StorageService storageService) {
        this.commentService = commentService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.storageService = storageService;
    }

    @PostMapping("/addQuestionComment")
    public String saveQuestionComment(@ModelAttribute("Comment") Comment comment,
                                      @RequestParam("questionId") Long questionId){
        if(comment.getId() != null){
            commentService.updateComment(comment);
        } else {
            commentService.saveQuestionComment(comment, questionId);
        }
        return "redirect:/question/" + questionId;
    }

    @PostMapping("/addAnswerComment")
    public String saveAnswerComment(@ModelAttribute("Comment") Comment comment,
                                      @RequestParam("answerId") Long answerId,
                                      @RequestParam("questionId") Long questionId){
        if(comment.getId() != null){
            commentService.updateComment(comment);
        } else {
            commentService.saveAnswerComment(comment, answerId);
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

    @GetMapping("/updateQuestionComment/{commentId}")
    public String updateQuestionComment(@PathVariable("commentId") Long commentId, Model model){
        Comment comment = commentService.findById(commentId);
        Question question = questionService.findById(comment.getQuestion().getId());
        model.addAttribute("question", question);
        model.addAttribute("Comment", comment);
        model.addAttribute("answer", new Answer());
        String base64Data = "";
        if (question.getImageFileName() != null) {
            byte[] data = storageService.getFileByName(question.getImageFileName());
            base64Data = Base64.getEncoder().encodeToString(data);
        }
        model.addAttribute("base64Data", base64Data);
        model.addAttribute("fileType", "image/png");
        return "show-question";
    }
    @GetMapping("/updateAnswerComment/{commentId}/{questionId}")
    public String updateAnswerComment(@PathVariable("commentId") Long commentId, @PathVariable("questionId") Long questionId, Model model){
        Comment comment = commentService.findById(commentId);
        Answer answer = answerService.findById(comment.getAnswer().getId());
        Question question = questionService.findById(questionId);
        model.addAttribute("answer", answer);
        model.addAttribute("question", question);
        model.addAttribute("Comment", comment);
        String base64Data = "";
        if (question.getImageFileName() != null) {
            byte[] data = storageService.getFileByName(question.getImageFileName());
            base64Data = Base64.getEncoder().encodeToString(data);
        }
        model.addAttribute("base64Data", base64Data);
        model.addAttribute("fileType", "image/png");
        return "show-question";
    }
    @GetMapping("/deleteAnswerComment/{commentId}/{questionId}")
    public String deleteAnswerComment(@PathVariable("commentId") Long commentId, @PathVariable("questionId") Long questionId){
        Comment comment = commentService.findById(commentId);
        Answer Answer = answerService.findById(comment.getAnswer().getId());
        commentService.deleteComment(comment);
        return "redirect:/question/" + questionId;
    }
}
