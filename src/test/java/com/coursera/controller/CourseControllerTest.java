package com.coursera.controller;

import com.coursera.model.Course;
import com.coursera.service.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    @WithMockUser(username = "Vaibhav")
    void getCoursesPage() throws Exception {
        MvcResult courses = mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(
                        "courses"))
                .andReturn();
        assertIterableEquals(Collections.emptyList(), (Iterable<?>) courses.getModelAndView().getModel().get("courses"));
    }

    @Test
    @WithMockUser(username = "Vaibhav")
    void getCoursesPageWithData() throws Exception {
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(new Course(BigDecimal.ONE,"Category 1","Course 1","Description 1"));
        BDDMockito.given(courseService.getCourses()).willReturn(expectedCourses);
        MvcResult courses = mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(
                        "courses"))
                .andReturn();
        assertIterableEquals(expectedCourses, (Iterable<?>) courses.getModelAndView().getModel().get("courses"));
    }
}