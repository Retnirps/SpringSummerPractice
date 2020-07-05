package com.example.TestServer.services;

import com.example.TestServer.entities.User;
import com.example.TestServer.exceptions.ResourceNotFoundException;
import com.example.TestServer.models.UserModel;
import com.example.TestServer.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private UserRepository userRepository;
    //add user
    public int addUser(UserModel userModel) {
        String username = userModel.getUsername();
        String email = userModel.getEmail();
        return this.userRepository.save(new User(username, email)).getUserId();
    }

    //getUserById
    public UserModel getUserById(int id) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("user ID:" + id + " not found"));
            String username = user.getUsername();
            int statusId = user.getStatusId();
            String email = user.getEmail();
            UserModel userModel = new UserModel(username, statusId, email);
            return userModel;
    }
    //setStatusForUser
}
