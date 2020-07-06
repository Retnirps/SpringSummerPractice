package com.example.TestServer.controllers;

import com.example.TestServer.dto.UserDto;
import com.example.TestServer.dto.UserStatusDto;
import com.example.TestServer.exceptions.ResourceNotFoundException;
import com.example.TestServer.models.UserModel;
import com.example.TestServer.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
public class MainController {
    @Autowired
    private Service service;

    //get users
    /*@GetMapping("/users")
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }*/

    //add user
    @PostMapping("/users")
    public int addUser(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String email = userDto.getEmail();
        UserModel userModel = new UserModel(username, email);
        return this.service.addUser(userModel);
    }

    //get user by id
    @GetMapping("/users/{id}")
    public UserDto getUserById(@PathVariable(value = "id") Integer userId)
    throws ResourceNotFoundException {
        UserModel userModel = this.service.getUserById(userId);
        String username = userModel.getUsername();
        String email = userModel.getEmail();
        UserDto userDto = new UserDto(username, email);
        return userDto;
    }

    @PutMapping("/users")
    public UserStatusDto setStatusForId(@RequestBody UserStatusDto userStatusDto) {
        int userId = userStatusDto.getUserId();
        int statusId = userStatusDto.getStatus().equals("Offline") ? 0 : 1;
        UserModel userModel = this.service.setStatusForId(userId, statusId);
        userStatusDto = new UserStatusDto(userId, userModel.getOldStatusId(), statusId);
        return userStatusDto;
    }

    //update user
    /*@PutMapping("/users/{id}")
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
    }*/
}
