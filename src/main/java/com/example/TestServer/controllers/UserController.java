package com.example.TestServer.controllers;

import com.example.TestServer.dto.StatisticsDto;
import com.example.TestServer.dto.UserDto;
import com.example.TestServer.dto.UserStatusDto;
import com.example.TestServer.exceptions.ResourceNotFoundException;
import com.example.TestServer.models.StatisticsModel;
import com.example.TestServer.models.Triple;
import com.example.TestServer.models.UserModel;
import com.example.TestServer.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
@Api(value = "UserController - catch all url linking with user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ApiOperation(value = "Add User")
    public Map addUser(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String email = userDto.getEmail();
        UserModel userModel = new UserModel(username, email);
        return this.userService.addUser(userModel);
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "Get User by some Id")
    public UserDto getUserById(@PathVariable(value = "id") Long userId)
    throws ResourceNotFoundException {
        UserModel userModel = this.userService.getUserById(userId);
        String username = userModel.getUsername();
        String email = userModel.getEmail();
        UserDto userDto = new UserDto(userId, username, email);
        return userDto;
    }

    @PutMapping("/users")
    @ApiOperation(value = "Set Online/Offline Status for User with Id")
    public UserStatusDto setStatusForId(@RequestBody UserStatusDto userStatusDto) {
        long userId = userStatusDto.getUserId();
        int statusId = userStatusDto.getStatus().equals("OFFLINE") ? 0 : 1;
        UserModel userModel = this.userService.setStatusForId(userId, statusId);
        userStatusDto = new UserStatusDto(userId, userModel.getOldStatusId(), statusId);
        return userStatusDto;
    }

    @GetMapping("/statistics")
    @ApiOperation(value = "Get Statistics of Changing Statuses After some Timestamp")
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
