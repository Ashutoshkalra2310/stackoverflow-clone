package io.mountblue.stackoverflowclone.controller;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
    @GetMapping("/askQuestion")
    public String showAskQuestionForm(Model model){
        model.addAttribute("question", new Question());
        return "add-question";
    }

    @PostMapping("/saveQuestion")
    public String saveQuestion(@ModelAttribute("question") Question question, @RequestParam("tagList") String tagList){
        if(question.getId() != null){
            questionService.updateQuestion(question, tagList);
        }
        return "redirect:show-question";
    }

    @GetMapping("/deleteQuestion/{questionId}")
    public String deleteQuestion(@PathVariable("questionId") Long id){
        questionService.deleteQuestion(id);
        return "redirect:show-question";
    }
}
