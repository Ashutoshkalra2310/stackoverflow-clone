package io.mountblue.stackoverflowclone.controller;

import io.mountblue.stackoverflowclone.entity.Answer;
import io.mountblue.stackoverflowclone.service.AnswerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AnswerController {
    private final AnswerService answerService;
    public AnswerController(AnswerService answerService){
        this.answerService=answerService;
    }

    @PostMapping("/saveAnswer")
    public String saveAnswer(@ModelAttribute("answer") Answer answer,
                             @RequestParam("questionId") Long questionId){
        answerService.saveAnswer(answer, questionId);
        return "redirect:/question/" + questionId;
    }

    @PostMapping("/updateAnswer/{answerId}")
    public String updateAnswer(@PathVariable("answerId") Long id,@ModelAttribute("answer") Answer updatedAnswer){
        answerService.updateAnswer(id,updatedAnswer);
        return "redirect:show-question";
    }

    @GetMapping("/deleteAnswer/{answerId}/{questionId}")
    public String deleteAnswer(@PathVariable("answerId") Long id,
                               @PathVariable("questionId") Long questionId){
        answerService.deleteAnswer(id);
        return "redirect:/question/" + questionId;
    }

}


