package io.mountblue.stackoverflowclone.controller;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.Tag;
import io.mountblue.stackoverflowclone.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class QuestionController {
    private final QuestionService questionService;
    public QuestionController(QuestionService theQuestionService){
        questionService  = theQuestionService;
    }
//    @GetMapping("/questions")
//    public String showQuestions(Model model){
//
//    }
    @GetMapping("/question")
    public String showQuestion(Model model,
                               @RequestParam(value = "detailedProblem", required = false) String detailedProblem,
                               @RequestParam(value = "expectingResults", required = false) String expectingResults,
                               @RequestParam(value = "tagList", required = false) String tags,
//                               @RequestParam(value = "showTitle", required = false) String showTitle,
//                               @RequestParam(value = "showDetailedProblem", required = false) String showDetailedProblem,
//                               @RequestParam(value = "showExpectingResults", required = false) String showExpectingResults,
//                               @RequestParam(value = "showTags", required = false) String showTags,
                               @ModelAttribute(value = "question") Question question
                               ){

        if(question == null) {
            question = new Question();
        }

        if(detailedProblem != null){
            question.setContent(detailedProblem);
        }
        if(expectingResults != null){
            String content = question.getContent();
            content=content + "<br><br>" + expectingResults;
            question.setContent(content);
        }
        model.addAttribute("saveQ", false);
        if(tags != null && !tags.isEmpty() && expectingResults != null && !expectingResults.isEmpty() && detailedProblem != null && !detailedProblem.isEmpty()){
            model.addAttribute("saveQ", true);

        }
        model.addAttribute("detailedProblem", detailedProblem == null ? "" : detailedProblem);
        model.addAttribute("expectingResults", expectingResults == null ? "" : expectingResults);
        model.addAttribute("tagList", tags == null ? "" : tags);

//        model.addAttribute("showTitle",true);
//        model.addAttribute("showDetailedProblem", true);
//        model.addAttribute("showExpectingResults", true);
//        model.addAttribute("showTags", true);
        model.addAttribute("question", question);
        return "add-question";
    }


    @GetMapping("/reviewQuestion")
    public String reviewQuestion(@ModelAttribute("question") Question question,
                                 @RequestParam("tagList") String tags
                                 ,Model model){
        model.addAttribute("question", question);
        model.addAttribute("tagList", tags);
        return "review-question";
    }
    @PostMapping("/saveQuestion")
    public String saveQuestion(@ModelAttribute("question") Question question, @RequestParam("tagList") String tags){
        if(tags != null && !tags.isEmpty()){
            String[] tagsArray = tags.split(",");
            Set<Tag> tagList = new HashSet<>();
            for(String tagString : tagsArray){
                tagString = tagString.trim();
                Tag newTag = new Tag();
                newTag.setName(tagString);
                tagList.add(newTag);
            }
            question.setTags(tagList);
        }
        questionService.save(question);
        return "redirect:/question";
    }

}
