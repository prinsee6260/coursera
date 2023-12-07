package com.coursera.repository;

import com.coursera.model.UserCourseDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserCourseDtlRepository extends JpaRepository<UserCourseDtl, BigDecimal> {

    Optional<UserCourseDtl> findByUserIdAndCourseId(BigDecimal userId, BigDecimal courseId);

    List<UserCourseDtl> findByUserId(BigDecimal id);
}
