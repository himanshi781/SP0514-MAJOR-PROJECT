package com.example.attendance.controller;

import com.example.attendance.model.User;
import com.example.attendance.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
