package com.example.kanye.user.controller;

import com.example.kanye.user.service.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // This should be a login landing page where the user can enter info to log in or register
    @GetMapping(path="/")
    public Object login() {
        // FIXME: delete these hardcoded info and replace by user input on web page
        String email = "helenchoi@gmail.com";
        String password = "helenchoi";

        // 1. Verify user info against our USERS database
        this.loginService.verifyUser(email, password);

        return "logged in";
    }

}
