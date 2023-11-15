package com.coursera.security;

import com.coursera.model.User;
import com.coursera.repository.UserRepository;
import com.coursera.util.Role;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.lang.reflect.Executable;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test UsernameNotFoundException")
    void loadUserByUsernameThrowException() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("Vaibhav")
        );
    }

    @Test
    @DisplayName("Test Authenticated User is returned")
    void loadUserByUsername() {
        User user = new User();
        user.setUserName("Vaibhav");
        user.setRole(Role.ADMIN);
        user.setPassword("Admin@123");
        Mockito.when(userRepository.findByUserName(Mockito.anyString()))
                .thenReturn(Optional.of(user));
        UserDetails vaibhav = assertDoesNotThrow(
                () -> customUserDetailsService.loadUserByUsername("Vaibhav")
        );
        assertEquals(user.getUserName(),vaibhav.getUsername());
    }

    @Test
    @DisplayName("Test Authenticated User without a Role is returned")
    void loadUserByUsernameWithRoleCheck() {
        User user = new User();
        user.setUserName("Vaibhav");
        user.setPassword("Admin@123");
        Mockito.when(userRepository.findByUserName(Mockito.anyString()))
                .thenReturn(Optional.of(user));
        assertThrows(IllegalArgumentException.class,
                () -> customUserDetailsService.loadUserByUsername("Vaibhav")
        );
    }

}