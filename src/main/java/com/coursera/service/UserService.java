package com.coursera.service;

import com.coursera.model.User;
import com.coursera.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(Optional<BigDecimal> id) {
        return userRepository.findById(id.orElseThrow(IllegalArgumentException::new))
                .orElseThrow(()->new UsernameNotFoundException("User not found with "+id.get()));
    }

    public User getUser(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(()->new UsernameNotFoundException("User not found with "+userName));
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(Optional<BigDecimal> id) {
        userRepository.deleteById(id.orElseThrow(IllegalArgumentException::new));
    }
}
