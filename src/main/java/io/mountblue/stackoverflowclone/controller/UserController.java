package io.mountblue.stackoverflowclone.controller;

import io.mountblue.stackoverflowclone.entity.User;
import io.mountblue.stackoverflowclone.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}
