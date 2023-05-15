package gic.i4b.group6.CafeManagement.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gic.i4b.group6.CafeManagement.services.UserService;

@Controller
public class LoginController {
    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    @PostMapping("/login/user")
    public String getLoginData(@RequestParam("username") String username, @RequestParam("password") String password) {
        if(userService.login(username, password).equals("Cashier")) {
            return "TableSelection";
        }
        else if(userService.login(username, password).equals("Admin")) {
            return "AdminHome";
        }
        return "redirect:/login?invalid";
    }
}
