package com.coursera.service;

import com.coursera.exception.CourseNotFoundException;
import com.coursera.model.Course;
import com.coursera.model.User;
import com.coursera.model.UserCourseDtl;
import com.coursera.repository.CourseRepository;
import com.coursera.repository.UserCourseDtlRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final UserCourseDtlRepository userCourseDtlRepository;

    private final UserService userService;

    public CourseService(CourseRepository userRepository, UserCourseDtlRepository userCourseDtlRepository, UserService userService) {
        this.courseRepository = userRepository;
        this.userCourseDtlRepository = userCourseDtlRepository;
        this.userService = userService;
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

    public void enrollCourse(String userName, Optional<BigDecimal> id) {
        User user = userService.getUser(userName);
        Course course = getCourse(id);
        UserCourseDtl userCourseDtl =
                userCourseDtlRepository.findByUserIdAndCourseId(user.getId(),course.getId())
                        .orElse(new UserCourseDtl());
        if (userCourseDtl.getId() == null) {
            userCourseDtl.setCourseId(course.getId());
            userCourseDtl.setUserId(user.getId());
            userCourseDtl.setEnrollmentDate(new Date());
            userCourseDtlRepository.save(userCourseDtl);
        }
    }
}
