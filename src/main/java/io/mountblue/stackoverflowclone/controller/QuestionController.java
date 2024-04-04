package io.mountblue.stackoverflowclone.controller;

import io.mountblue.stackoverflowclone.entity.*;
import io.mountblue.stackoverflowclone.service.*;
import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.View;
import io.mountblue.stackoverflowclone.entity.Tag;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.HashSet;
import java.util.Set;


@Controller
public class QuestionController {
    private final QuestionService questionService;
    private final ViewService viewService;
    private final TagService tagService;
    private final UserService userService;
    private final StorageService storageService;

    public QuestionController(QuestionService questionService, ViewService viewService, TagService tagService, UserService userService, StorageService storageService) {
        this.questionService = questionService;
        this.viewService = viewService;
        this.tagService = tagService;
        this.userService = userService;
        this.storageService = storageService;
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
    public String saveQuestion(@ModelAttribute("question") Question question,
                               @RequestParam(value = "image" , required = false)MultipartFile file,
                               @RequestParam("tagList") String tags){
        if(question.getId() != null){
            questionService.updateQuestion(question,file, tags);
        }
        else {
            questionService.save(question, file, tags);
        }
        return "redirect:/allQuestions";
    }

    @GetMapping("/showQuestionToUpdate/{questionId}")
    public String updateQuestion(@PathVariable("questionId") Long questionId, Model model){
        Question question = questionService.findById(questionId);
        model.addAttribute("question", question);
        List<Tag> tagList = question.getTags();
        StringBuilder tagString = new StringBuilder();
        for(Tag tag : tagList){
            tagString.append(tag.getName()).append(" ,");
        }
        model.addAttribute("tagList", tagString.substring(0, tagString.length()-1));
        return "review-question";
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
    public String showQuestion(Model model,@PathVariable("questionId") Long id, @AuthenticationPrincipal UserDetails userDetails){
        Question question = questionService.findById(id);
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
            viewService.addView(question);
        }
        String base64Data="";
        if(question.getImageFileName()!=null){
        byte[] data = storageService.getFileByName(question.getImageFileName());
        base64Data = Base64.getEncoder().encodeToString(data);
        }
        model.addAttribute("base64Data", base64Data);
        model.addAttribute("fileType", "image/png");
        model.addAttribute("question", question);
        model.addAttribute("Comment", new Comment());
        model.addAttribute("answer", new Answer());
        if(userDetails != null){
            model.addAttribute("currentUser", userDetails.getUsername());
        }
        return "show-question";
    }
    @GetMapping("/filters")
    public String filterQuestions(@RequestParam(name = "noAnswer", required = false, defaultValue = "false") boolean noAnswer,
                                  @RequestParam(name = "noAcceptedAnswer", required = false, defaultValue = "false") boolean noAcceptedAnswer,
                                  @RequestParam(name = "sortBy", required = false, defaultValue = "newest") String sortBy,
                                  @RequestParam(name = "tagSearch", required = false) String tagSearch,
                                  Model model){
        List<Question> questions = questionService.filterQuestion(noAnswer, noAcceptedAnswer, sortBy, tagSearch);
        model.addAttribute("questions", questions);
        model.addAttribute("noAnswer", noAnswer);
        model.addAttribute("noAcceptedAnswer", noAcceptedAnswer);
        model.addAttribute("sortBy", "newest");
        model.addAttribute("tagSearch", "");
        return "all-question";
    }

    @GetMapping("/searchQuestion")
    public String searchQuestion(@RequestParam(value = "keyword", required = false) String keyword,
                                 Model model) {
        List<Question> searchResults;
        if(keyword.isEmpty()){
            searchResults = questionService.findAll();
        } else {
            searchResults = questionService.search(keyword);
        }
        model.addAttribute("questions", searchResults);
        model.addAttribute("noAnswer", false);
        model.addAttribute("noAcceptedAnswer", false);
        model.addAttribute("sortBy", "newest");
        model.addAttribute("tagSearch", "");
        model.addAttribute("keyword", keyword);
        return "all-question";
    }

    @GetMapping("/homepage")
    public String homepage(){
        return "home-page";
    }
    @GetMapping("/tagList")
    public String tag(Model model){
        List<Tag> tags =tagService.findAll();
        model.addAttribute("tags", tags);
        return "tags-list";
    }
    @GetMapping("/userList")
    public String user(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }
}
