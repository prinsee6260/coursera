package com.coursera.service;

import com.coursera.exception.CourseNotFoundException;
import com.coursera.model.Course;
import com.coursera.model.User;
import com.coursera.model.UserCourseDtl;
import com.coursera.repository.CourseRepository;
import com.coursera.repository.CustomJpaRepository;
import com.coursera.repository.UserCourseDtlRepository;
import com.coursera.vo.StudentEnrollments;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    private final CustomJpaRepository customJpaRepository;

    private final UserCourseDtlRepository userCourseDtlRepository;

    private final CourseRepository courseRepository;

    private final UserService userService;

    public EnrollmentService(CustomJpaRepository customJpaRepository, UserCourseDtlRepository userCourseDtlRepository1, CourseRepository courseRepository, UserService userService) {
        this.customJpaRepository = customJpaRepository;
        this.userCourseDtlRepository = userCourseDtlRepository1;
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    public List<StudentEnrollments> getEnrollments() {
        return customJpaRepository.findAllEnrollments();
    }

    public List<Course> getEnrollmentsByUserName(String name) {
        User user = userService.getUser(name);
        List<UserCourseDtl> userCourseDtlList = userCourseDtlRepository.findByUserId(user.getId());
        if(userCourseDtlList.isEmpty())
            return Collections.emptyList();
        List<BigDecimal> courseIdList = userCourseDtlList.stream().map(UserCourseDtl::getCourseId).collect(Collectors.toList());
        return courseRepository.findAllById(courseIdList);
    }

    public void disenrollCourse(String userName, Optional<BigDecimal> id) {
        User user = userService.getUser(userName);
        UserCourseDtl userCourseDtl = userCourseDtlRepository
                .findByUserIdAndCourseId(user.getId(),id.orElseThrow(IllegalArgumentException::new))
                .orElseThrow(() -> new CourseNotFoundException("\"Enrolled Course not found with id "+id));
        userCourseDtlRepository.delete(userCourseDtl);
    }
}
