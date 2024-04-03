package io.mountblue.stackoverflowclone.controller;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.User;
import io.mountblue.stackoverflowclone.service.QuestionService;
import io.mountblue.stackoverflowclone.service.TagService;
import io.mountblue.stackoverflowclone.service.TagServiceImpl;
import io.mountblue.stackoverflowclone.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final QuestionService questionService;
    private final TagService tagService;
    public UserController(UserService userService, QuestionService questionService, TagService tagService) {
        this.userService = userService;
        this.questionService = questionService;
        this.tagService = tagService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @GetMapping("/register")
    public String showRegistration(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user, Model model){
        if(userService.isUserExist(user.getEmail())){
            model.addAttribute("error", "Username already exist. Please use different username");
            return "registration";
        } else {
            userService.saveUser(user);
        }
        return "redirect:/login";
    }
    @GetMapping("/showUser")
    public String showUser(Model model, @RequestParam("userId") Long userId){
        List<Question> questionList = userService.findByUserId(userId);
        model.addAttribute("questions", questionList);
        return "all-question";
    }
    @GetMapping("/showTag")
    public String showTag(Model model, @RequestParam("tagId") Long tagId){
        List<Question> questionList = tagService.findQuestionByTagName(tagId);
        model.addAttribute("questions", questionList);
        return "all-question";
    }
}
