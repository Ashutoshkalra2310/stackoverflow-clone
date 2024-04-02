package io.mountblue.stackoverflowclone.controller;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.View;
import io.mountblue.stackoverflowclone.service.QuestionService;
import io.mountblue.stackoverflowclone.service.ViewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class QuestionController {
    private final QuestionService questionService;
    private final ViewService viewService;

    public QuestionController(QuestionService questionService, ViewService viewService) {
        this.questionService = questionService;
        this.viewService = viewService;
    }
    @GetMapping({"/allQuestions", "/"})
    public String showAllQuestions(Model model){
        List<Question> questions =  questionService.getAllQuestions();
        model.addAttribute("questions", questions);
        model.addAttribute("noAnswer", false);
        model.addAttribute("noAcceptedAnswer", false);
        model.addAttribute("newest", false);
        model.addAttribute("oldest", false);
        model.addAttribute("recentActivity", false);
        model.addAttribute("tagSearch", "");
        return "all-question";
    }
    @GetMapping("/addQuestion")
    public String addQuestion(Model model){
        model.addAttribute("detailedProblem", "");
        model.addAttribute("expectingResults", "");
        model.addAttribute("tagList", "");
        model.addAttribute("question", new Question());
        return "add-question";
    }


    @GetMapping("/reviewQuestion")
    public String reviewQuestion(@ModelAttribute("question") Question question,
                                 @RequestParam("tagList") String tags,
                                 @RequestParam(value = "detailedProblem", required = false) String detailedProblem,
                                 @RequestParam(value = "expectingResults", required = false) String expectingResults
                                 ,Model model){
        if(detailedProblem != null){
            question.setContent(detailedProblem);
        }
        if(expectingResults != null){
            String content = question.getContent();
            content=content + "<br>" + expectingResults;
            question.setContent(content);
        }
        model.addAttribute("question", question);
        model.addAttribute("tagList", tags);
        return "review-question";
    }
    @PostMapping("/saveQuestion")
    public String saveQuestion(@ModelAttribute("question") Question question, @RequestParam("tagList") String tags){
        if(question.getId() != null){
            questionService.updateQuestion(question, tags);
        }
        else {
            questionService.save(question, tags);
        }
        return "redirect:/allQuestions";
    }

    @GetMapping("/askQuestion")
    public String showAskQuestionForm(Model model){
        model.addAttribute("question", new Question());
        return "add-question";
    }
    @GetMapping("/deleteQuestion/{questionId}")
    public String deleteQuestion(@PathVariable("questionId") Long id){
        questionService.deleteQuestion(id);
        return "redirect:/allQuestions";
    }
    @GetMapping("/question/{questionId}")
    public String showQuestion(Model model,@PathVariable("questionId") Long id){
        Question question = questionService.findById(id);
        model.addAttribute(question);
        return "showQuestion";
    }

    @GetMapping("/filters")
    public String filterQuestions(@RequestParam(name = "noAnswer", required = false, defaultValue = "false") boolean noAnswer,
                                  @RequestParam(name = "noAcceptedAnswer", required = false, defaultValue = "false") boolean noAcceptedAnswer,
                                  @RequestParam(name = "newest", required = false, defaultValue = "false") boolean newest,
                                  @RequestParam(name = "oldest", required = false, defaultValue = "false") boolean oldest,
                                  @RequestParam(name = "recentActivity", required = false, defaultValue = "false") boolean recentActivity,
                                  @RequestParam(name = "tagSearch", required = false) String tagSearch,
                                  Model model){

        List<Question> questions = questionService.filterQuestion(noAnswer, noAcceptedAnswer, newest, oldest, recentActivity, tagSearch);
        model.addAttribute("questions", questions);
        model.addAttribute("noAnswer", noAnswer);
        model.addAttribute("noAcceptedAnswer", noAcceptedAnswer);
        model.addAttribute("newest", newest);
        model.addAttribute("oldest", oldest);
        model.addAttribute("recentActivity", recentActivity);
        model.addAttribute("tagSearch", tagSearch);
        return "all-question";
    }

    @GetMapping("/showQuestion/{questionId}")
    public String showQuestion(@PathVariable("questionId") Long id, Model model){
        Question question = questionService.findById(id);
        View view = viewService.findByUserAndQuestion(/*User user,*/ question);
        if(view == null){
            view = new View();
            view.setQuestion(question);
//            view.setUser(user);
            viewService.save(view);
            question.setViewCount(question.getViewCount() + 1);
            questionService.save(question);
        }
        model.addAttribute("question", question);
        return "show-question";
    }

}
