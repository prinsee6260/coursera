package com.coursera.service;

import com.coursera.exception.CourseNotFoundException;
import com.coursera.model.Course;
import com.coursera.model.User;
import com.coursera.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository userRepository) {
        this.courseRepository = userRepository;
    }

    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course getCourse(Optional<BigDecimal> id) {
        return courseRepository.findById(id.orElseThrow(IllegalArgumentException::new))
                .orElseThrow(() -> new CourseNotFoundException("Course not found with "+id.get()));
    }
}
