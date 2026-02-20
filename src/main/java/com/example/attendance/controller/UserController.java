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

    // CREATE
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // READ
    @GetMapping
    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // UPDATE
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setFraudScore(updatedUser.getFraudScore());
        user.setFlagged(updatedUser.isFlagged());

        return userRepository.save(user);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }
}
