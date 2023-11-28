package com.coursera.controller;

import com.coursera.service.CourseService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courses")
@Secured({"ROLE_ADMIN","ROLE_STUDENT"})
public class CourseController {


    private static final String COURSE_FOLDER = "course/";

    public final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String getCoursesPage(Model model){
        model.addAttribute("courses",courseService.getCourses());
        return COURSE_FOLDER + "courses";
    }

}
