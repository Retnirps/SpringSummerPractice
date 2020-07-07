package com.example.TestServer.services;

import com.example.TestServer.entities.RequestLog;
import com.example.TestServer.entities.Statistics;
import com.example.TestServer.entities.User;
import com.example.TestServer.exceptions.ResourceNotFoundException;
import com.example.TestServer.models.Pair;
import com.example.TestServer.models.StatisticsModel;
import com.example.TestServer.models.UserModel;
import com.example.TestServer.repos.RequestLogRepository;
import com.example.TestServer.repos.StatisticsRepository;
import com.example.TestServer.repos.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@org.springframework.stereotype.Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private RequestLogRepository requestLogRepository;

    public long addUser(UserModel userModel) {
        String username = userModel.getUsername();
        String email = userModel.getEmail();
        return this.userRepository.save(new User(username, email)).getUserId();
    }

    public UserModel getUserById(long id) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("user ID:" + id + " not found"));
            String username = user.getUsername();
            String email = user.getEmail();
            UserModel userModel = new UserModel(username, email);
            return userModel;
    }

    public UserModel setStatusForId(long id, int statusId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user ID:" + id + " not found"));
        int oldStatusId = user.getStatusId();
        user.setStatusId(statusId);
        final User updatedUser = userRepository.save(user);
        statisticsRepository.save(new Statistics(updatedUser, statusId, Instant.now().getEpochSecond()));

        UserModel userModel = new UserModel(id, oldStatusId, statusId);
        return userModel;
    }

    public Pair<Long, List<StatisticsModel>> getUsersChangedStatusAfterTimestamp(String status, long timestamp) {
        int statusId = status.equals("Offline") ? 0 : 1;
        List<Statistics> statistics = this.statisticsRepository.findAllByChangeTimeAfterAndStatusId(timestamp, statusId);
        List<StatisticsModel> statisticsModels = new ArrayList<>();
        for (Statistics statistic : statistics) {
            String username = statistic.getUser().getUsername();
            String email = statistic.getUser().getEmail();
            int currentStatusId = statistic.getStatusId();
            long changeTime = statistic.getChangeTime();
            StatisticsModel statisticsModel = new StatisticsModel(username, email, currentStatusId, changeTime);
            statisticsModels.add(statisticsModel);
        }
        long requestId = requestLogRepository.save(new RequestLog(Instant.now().getEpochSecond(), status, timestamp)).getId();
        return new Pair<>(requestId, statisticsModels);
    }
}
