package com.coursera.controller;

import com.coursera.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/users")
public class UserController {

    private static String USER_FOLDER = "user/";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsersPage(Model model){
        model.addAttribute("users", userService.getUsers());
        return USER_FOLDER + "users";
    }

}
