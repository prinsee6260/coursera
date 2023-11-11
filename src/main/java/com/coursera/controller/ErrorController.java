package com.coursera.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(AccessDeniedException.class)
    public String accessDeniedException(){
        return "accessDenied";
    }
}
