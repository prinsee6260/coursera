package com.coursera.service;

import com.coursera.exception.CourseNotFoundException;
import com.coursera.model.Course;
import com.coursera.model.User;
import com.coursera.model.UserCourseDtl;
import com.coursera.repository.CourseRepository;
import com.coursera.repository.UserCourseDtlRepository;
import com.coursera.security.AuthenticatedUser;
import com.coursera.util.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
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
        log.debug("getCourses");
        List<Course> courseList = null;
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getUser().getRole().equals(Role.STUDENT)){
            courseList = courseRepository.findByActiveTrue();
        }else {
            courseList = courseRepository.findAll();
        }
        log.debug("getCourses ended");
        return courseList;
    }

    public Course saveCourse(Course course) {
        log.debug("saveCourse with :: {}",course);
        Course save = courseRepository.save(course);
        log.debug("saveCourse ended with :: {}",save);
        return save;
    }

    public Course getCourse(Optional<BigDecimal> id) {
        log.debug("getCourse with :: {}",id);
        Course course = courseRepository.findById(id.orElseThrow(IllegalArgumentException::new))
                .orElseThrow(() -> new CourseNotFoundException("Course not found with " + id.get()));
        log.debug("getCourse ended with :: {}",course);
        return course;
    }

    public void enrollCourse(String userName, Optional<BigDecimal> id) {
        log.debug("enrollCourse with :: {} ,{}",userName,id);
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
            log.debug("enrollCourse :: courseEnrolled successfully");
        }
        log.debug("enrollCourse ended");

    }

    public void activateCourse(Optional<BigDecimal> id) {
        Course course = getCourse(id);
        course.setActive(!course.getActive());
        courseRepository.save(course);
    }
}
