package com.coursera.controller;

import com.coursera.exception.CourseNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ErrorControllerTest {

    private ErrorController errorController = new ErrorController();

    @Test
    void accessDeniedException() {
        String exceptionPage = errorController.accessDeniedException(Mockito.mock(Model.class));
        assertEquals("error",exceptionPage);
    }

    @Test
    void notFoundExe() {
        String usernameNotFoundExe = "Username not found exe";
        Model mock = Mockito.mock(Model.class);
        String exceptionMsg = errorController.notFoundExe(mock,new UsernameNotFoundException(
                usernameNotFoundExe));
        assertEquals("error",exceptionMsg);
    }

    @Test
    void courseNotFoundExe() {
        String usernameNotFoundExe = "Course not found exe";
        Model mock = Mockito.mock(Model.class);
        String exceptionMsg = errorController.notFoundExe(mock,new CourseNotFoundException(
                usernameNotFoundExe));
        assertEquals("error",exceptionMsg);
    }
}