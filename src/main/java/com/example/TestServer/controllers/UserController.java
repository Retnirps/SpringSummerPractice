package com.example.TestServer.controllers;

import com.example.TestServer.dto.StatisticsDto;
import com.example.TestServer.dto.UserDto;
import com.example.TestServer.dto.UserStatusDto;
import com.example.TestServer.exceptions.ResourceNotFoundException;
import com.example.TestServer.models.StatisticsModel;
import com.example.TestServer.models.Triple;
import com.example.TestServer.models.UserModel;
import com.example.TestServer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public Map addUser(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String email = userDto.getEmail();
        UserModel userModel = new UserModel(username, email);
        return this.userService.addUser(userModel);
    }

    @GetMapping("/users/{id}")
    public UserDto getUserById(@PathVariable(value = "id") Long userId)
    throws ResourceNotFoundException {
        UserModel userModel = this.userService.getUserById(userId);
        String username = userModel.getUsername();
        String email = userModel.getEmail();
        UserDto userDto = new UserDto(userId, username, email);
        return userDto;
    }

    @PutMapping("/users")
    public UserStatusDto setStatusForId(@RequestBody UserStatusDto userStatusDto) {
        long userId = userStatusDto.getUserId();
        int statusId = userStatusDto.getStatus().equals("OFFLINE") ? 0 : 1;
        UserModel userModel = this.userService.setStatusForId(userId, statusId);
        userStatusDto = new UserStatusDto(userId, userModel.getOldStatusId(), statusId);
        return userStatusDto;
    }

    @GetMapping("/statistics")
    public Triple<List<StatisticsDto>> getAllUsersChangedStatusAfterTimestamp(@RequestBody StatisticsDto statisticsDto) {
        String status = statisticsDto.getStatus();
        long timestamp = statisticsDto.getTimestamp();
        Triple<List<StatisticsModel>> requestIdWithRequestBody = userService.getUsersChangedStatusAfterTimestamp(status, timestamp);
        List<StatisticsModel> models = requestIdWithRequestBody.getUsersInfo();
        List<StatisticsDto> statisticsDtoList = new ArrayList<>();
        for (StatisticsModel model : models) {
            StatisticsDto outputDto = new StatisticsDto(model.getStatusId(), model.getTimestamp(), model.getUsername(), model.getEmail());
            statisticsDtoList.add(outputDto);
        }
        long requestId = requestIdWithRequestBody.getRequestId();
        long requestTime = requestIdWithRequestBody.getTimestamp();
        return new Triple<>(requestId, requestTime, statisticsDtoList);
    }
}
