package io.mountblue.stackoverflowclone.controller;

import io.mountblue.stackoverflowclone.entity.Answer;
import io.mountblue.stackoverflowclone.service.AnswerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AnswerController {
    private final AnswerServiceImpl answerService;
    @Autowired
    public AnswerController(AnswerServiceImpl answerService){
        this.answerService=answerService;
    }

    @PostMapping("/saveAnswer")
    public String saveAnswer(@ModelAttribute("answer") Answer answer){
        answerService.saveAnswer(answer);
        return "redirect:show-question";
    }

    @PostMapping("/updateAnswer/{answerId}")
    public String updateAnswer(@PathVariable("answerId") Long id,@ModelAttribute("answer") Answer updatedAnswer){
        answerService.updateAnswer(id,updatedAnswer);
        return "redirect:show-question";
    }

    @GetMapping("/deleteAnswer/{answerId}")
    public String deleteAnswer(@PathVariable("answerId") Long id){
        answerService.deleteAnswer(id);
        return "redirect:show-question";
    }
    
}


