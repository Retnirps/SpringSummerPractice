package com.example.TestServer.controllers;

import com.example.TestServer.dto.*;
import com.example.TestServer.exceptions.ResourceNotFoundException;
import com.example.TestServer.models.StatisticsModel;
import com.example.TestServer.models.Triple;
import com.example.TestServer.models.UserModel;
import com.example.TestServer.services.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
@Api(value = "UserController - catch all url linking with user")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ApiOperation(value = "Add User")
    public Map addUser(@RequestBody RequestAddUser request) {
        String username = request.getUsername();
        String email = request.getEmail();
        UserModel userModel = new UserModel(username, email);
        return this.userService.addUser(userModel);
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "Get User by some Id")
    public ResponseGetUserById getUserById(@PathVariable(value = "id") Long userId)
    throws ResourceNotFoundException {
        UserModel userModel = this.userService.getUserById(userId);
        String username = userModel.getUsername();
        String email = userModel.getEmail();
        return new ResponseGetUserById(userId, username, email);
    }

    @PutMapping("/users")
    @ApiOperation(value = "Set Online/Offline Status for User with Id")
    public ResponseSetStatusForId setStatusForId(@RequestBody RequestSetStatusForId request) {
        long userId = request.getUserId();
        int statusId = request.getStatus().equals("OFFLINE") ? 0 : 1;
        UserModel userModel = this.userService.setStatusForId(userId, statusId);
        return new ResponseSetStatusForId(userId, userModel.getOldStatusId(), statusId);
    }

    @GetMapping("/statistics")
    @ApiOperation(value = "Get Statistics of Changing Statuses After some Timestamp")
    public Triple<List<ResponseStatisticsDto>> getAllUsersChangedStatusAfterTimestamp(@RequestParam String status, @RequestParam long timestamp) {
        Triple<List<StatisticsModel>> requestIdWithRequestBody = userService.getUsersChangedStatusAfterTimestamp(status, timestamp);
        List<StatisticsModel> models = requestIdWithRequestBody.getUsersInfo();
        List<ResponseStatisticsDto> responseStatisticsDtoList = new ArrayList<>();
        for (StatisticsModel model : models) {
            ResponseStatisticsDto userStatistics = new ResponseStatisticsDto(model.getStatusId(), model.getTimestamp(), model.getUsername(), model.getEmail());
            responseStatisticsDtoList.add(userStatistics);
        }
        long requestId = requestIdWithRequestBody.getRequestId();
        long requestTime = requestIdWithRequestBody.getTimestamp();
        return new Triple<>(requestId, requestTime, responseStatisticsDtoList);
    }
}
