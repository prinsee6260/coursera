package com.coursera.model;

import com.coursera.util.Role;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue
    private BigDecimal id;

    private String userName;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
