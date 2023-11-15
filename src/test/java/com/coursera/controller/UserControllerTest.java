package com.coursera.controller;

import com.coursera.model.User;
import com.coursera.service.UserService;
import com.coursera.util.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ArrayList<User> userList;

    @BeforeEach
    void setUp() {
        this.userList = new ArrayList<>();
        this.userList.add(new User(BigDecimal.ONE, "user1","user1@gmail.com", "pwd1",Role.STUDENT));
        this.userList.add(new User(new BigDecimal(2), "user2","2@gmail.com", "pwd2",Role.STUDENT));
        this.userList.add(new User(new BigDecimal(3), "user3","3@gmail.com", "pwd3", Role.STUDENT));
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    void getUsersPage() throws Exception {
        BDDMockito.given(userService.getUsers()).willReturn(userList);
        MvcResult users = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/users")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andReturn();
        Assertions.assertIterableEquals(userList, (Iterable<?>) users.getModelAndView().getModel().get("users"));
    }

}