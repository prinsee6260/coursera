package com.coursera.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(AccessDeniedException.class)
    public String accessDeniedException(Model model){
        model.addAttribute("message","Access Denied !");
        return "error";
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String useNotFoundExe(Model model,UsernameNotFoundException e){
        model.addAttribute("message",e.getMessage());
        return "error";
    }
}
