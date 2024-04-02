package io.mountblue.stackoverflowclone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/loginpage")
    public String login(){
        return "login";
    }
    @GetMapping("/registerpage")
    public String register(){
        return "registration";
    }
}
