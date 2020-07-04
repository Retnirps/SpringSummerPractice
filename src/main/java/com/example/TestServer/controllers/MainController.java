package com.example.TestServer.controllers;

import com.example.TestServer.exceptions.ResourceNotFoundException;
import com.example.TestServer.models.User;
import com.example.TestServer.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    //get users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    //get user by id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Integer userId)
    throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user ID:" + userId + " not found"));
        return ResponseEntity.ok().body(user);
    }

    //add user
    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        return this.userRepository.save(user);
    }

    //update user
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Integer userId,
                                           @Validated @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user ID:" + userId + " not found"));

        user.setEmail(userDetails.getEmail());
        user.setStatusId(userDetails.getStatusId());
        user.setUsername(userDetails.getUsername());

        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);

    }

    //delete user
    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Integer userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user ID:" + userId + " not found"));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
