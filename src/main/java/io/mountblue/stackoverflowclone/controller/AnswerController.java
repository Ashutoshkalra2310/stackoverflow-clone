package io.mountblue.stackoverflowclone.controller;

import io.mountblue.stackoverflowclone.entity.Answer;
import io.mountblue.stackoverflowclone.entity.Comment;
import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.Tag;
import io.mountblue.stackoverflowclone.service.AnswerService;
import io.mountblue.stackoverflowclone.service.QuestionService;
import io.mountblue.stackoverflowclone.service.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Controller
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final StorageService storageService;


    public AnswerController(AnswerService answerService, QuestionService questionService, StorageService storageService) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.storageService = storageService;
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
        String base64Data = "";
        if (question.getImageFileName() != null) {
            byte[] data = storageService.getFileByName(question.getImageFileName());
            base64Data = Base64.getEncoder().encodeToString(data);
        }
        model.addAttribute("base64Data", base64Data);
        model.addAttribute("fileType", "image/png");
        return "show-question";
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
        Question question = questionService.findById(questionId);
        question.setIsAnswered(Boolean.TRUE);
        questionService.save(question);
        return "redirect:/question/" +questionId;
    }
}


