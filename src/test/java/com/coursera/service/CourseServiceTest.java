package com.coursera.service;

import com.coursera.model.Course;
import com.coursera.model.User;
import com.coursera.repository.CourseRepository;
import com.coursera.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void getCourses() {
        List<Course> courses = courseService.getCourses();
        assertNotNull(courses);
    }

    @Test
    void getCoursesWithData() {
        List<Course> expected = new ArrayList<>();
        expected.add(new Course());
        when(courseRepository.findAll()).thenReturn(expected);
        List<Course> courses = courseService.getCourses();
        assertIterableEquals(expected,courses);
    }
}