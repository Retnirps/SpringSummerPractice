package com.example.TestServer.services;

import com.example.TestServer.models.StatisticsModel;
import com.example.TestServer.models.Triple;
import com.example.TestServer.models.UserModel;

import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public interface IUserService {
    Map addUser(UserModel userModel);
    UserModel getUserById(long id);
    UserModel setStatusForId(long id, int statusId);
    Triple<List<StatisticsModel>> getUsersChangedStatusAfterTimestamp(String status, long timestamp);
}
