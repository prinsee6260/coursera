package com.coursera.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "TBL_USER_COURSE_DTLS")
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseDtl {

    @Id
    @GeneratedValue
    private BigDecimal id;

    private BigDecimal userId;

    private BigDecimal courseId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date enrollmentDate;

}
