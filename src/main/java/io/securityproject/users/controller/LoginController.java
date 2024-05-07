package io.securityproject.users.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login() {
        return "login/login";
    }
    
    @GetMapping("/signup")
    public String signup() {
        return "login/signup";
    }
}
