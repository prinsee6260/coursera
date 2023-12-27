package com.coursera.controller;

import com.coursera.service.EnrollmentService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/enrollments")
@Secured("ROLE_ADMIN")
public class EnrollmentController {

    public static final String ENROLL_FOLDER = "enrollment/";
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public String getStudentEnrollments(Model model){
        model.addAttribute("enrollments",enrollmentService.getEnrollments());
        return ENROLL_FOLDER +"enrollments";
    }

    @GetMapping("/{name}")
    public String viewStudentEnrollment(Model model, @PathVariable String name){
        model.addAttribute("courses",enrollmentService.getEnrollmentsByUserName(name));
        model.addAttribute("name",name);
        return ENROLL_FOLDER + "showEnrollment";
    }

}
