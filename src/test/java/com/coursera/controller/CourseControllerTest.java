package com.coursera.controller;

import com.coursera.exception.CourseNotFoundException;
import com.coursera.model.Course;
import com.coursera.service.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CourseController.class)
@WithMockUser(username = "Vaibhav", authorities = "ADMIN")
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
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
        expectedCourses.add(new Course(BigDecimal.ONE, "Category 1", "Course 1", "Description 1",null,true));
        BDDMockito.given(courseService.getCourses()).willReturn(expectedCourses);
        MvcResult courses = mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(
                        "courses"))
                .andReturn();
        assertIterableEquals(expectedCourses, (Iterable<?>) courses.getModelAndView().getModel().get("courses"));
    }

    @Test
    void getCoursesCreatePage() throws Exception {
        MvcResult courses = mockMvc.perform(MockMvcRequestBuilders.get("/courses/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(
                        "course"))
                .andReturn();
        assertNull(((Course) courses.getModelAndView().getModel().get("course")).getId());
    }

    @Test
    void getCoursesUpdatePage() throws Exception {
        BDDMockito.given(courseService.getCourse(BDDMockito.any(Optional.class))).willThrow(CourseNotFoundException.class);
        MvcResult courses = mockMvc.perform(MockMvcRequestBuilders.get("/courses/1.0/update"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andReturn();
    }

    @Test
    void getCoursesUpdatePageWithCorrectResponse() throws Exception {
        BDDMockito.given(courseService.getCourse(BDDMockito.any(Optional.class))).willReturn(
                new Course(BigDecimal.ONE, "Category 1", "Course 1", "Description 1",null,true));
        MvcResult courses = mockMvc.perform(MockMvcRequestBuilders.get("/courses/1.0/update"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("course/course"))
                .andReturn();
    }

    @Test
    void saveCourse() throws Exception {
        MvcResult users = mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":\"\"," +
                                "\"name\":\"Vaibhav\", \"description\":\"v@gmail.com\"," +
                                "\"category\":\"STUDENT\"" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/courses"))
                .andReturn();
    }

}