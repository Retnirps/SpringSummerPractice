package com.example.TestServer.services;

import com.example.TestServer.entities.User;
import com.example.TestServer.exceptions.ResourceNotFoundException;
import com.example.TestServer.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private UserRepository userRepository;
    //add user
    public int addUser(String username, String email) {
        return this.userRepository.save(new User(username, email)).getUserId();
    }

    //getUserById
    public ResponseEntity<User> getUserById(int id) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("user ID:" + id + " not found"));
            return ResponseEntity.ok().body(user);
    }
    //setStatusForUser
}
