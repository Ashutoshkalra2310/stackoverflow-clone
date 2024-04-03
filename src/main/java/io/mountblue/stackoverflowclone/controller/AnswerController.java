package io.mountblue.stackoverflowclone.controller;

import io.mountblue.stackoverflowclone.entity.Answer;
import io.mountblue.stackoverflowclone.entity.Comment;
import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.Tag;
import io.mountblue.stackoverflowclone.service.AnswerService;
import io.mountblue.stackoverflowclone.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;


    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping("/saveAnswer")
    public String saveAnswer(@ModelAttribute("answer") Answer answer,
                             @RequestParam("questionId") Long questionId){
        if(answer.getId() != null){
            answerService.updateAnswer(answer);
        }
        answerService.saveAnswer(answer, questionId);
        return "redirect:/question/" + questionId;
    }

    @GetMapping("/showAnswerToUpdate/{answerId}")
    public String updateQuestion(@PathVariable("answerId") Long answerId, Model model){
        Answer answer = answerService.findById(answerId);
        Question question = questionService.findById(answer.getQuestion().getId());
        model.addAttribute("question", question);
        model.addAttribute("Comment", new Comment());
        model.addAttribute("answer", answer);
        return "showQuestion";
    }

    @GetMapping("/deleteAnswer/{answerId}/{questionId}")
    public String deleteAnswer(@PathVariable("answerId") Long answerId,
                               @PathVariable("questionId") Long questionId){
        answerService.deleteAnswer(answerId);
        return "redirect:/question/" + questionId;
    }

    @GetMapping("/markCorrectAnswer/{answerId}/{questionId}")
    public String markCorrectAnswer(@PathVariable("answerId") Long answerId, @PathVariable("questionId") Long questionId){
        answerService.markCorrectAnswer(answerId);
        return "redirect:/question/" +questionId;
    }
}


