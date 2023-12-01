package com.coursera.service;

import com.coursera.model.Course;
import com.coursera.model.User;
import com.coursera.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository userRepository) {
        this.courseRepository = userRepository;
    }

    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }
}
