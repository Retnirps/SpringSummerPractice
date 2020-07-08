package com.example.TestServer.services;

import com.example.TestServer.entities.RequestLog;
import com.example.TestServer.entities.Statistics;
import com.example.TestServer.entities.User;
import com.example.TestServer.exceptions.ResourceNotFoundException;
import com.example.TestServer.models.StatisticsModel;
import com.example.TestServer.models.Triple;
import com.example.TestServer.models.UserModel;
import com.example.TestServer.repos.RequestLogRepository;
import com.example.TestServer.repos.StatisticsRepository;
import com.example.TestServer.repos.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class UserService {
    private final UserRepository userRepository;

    private final StatisticsRepository statisticsRepository;

    private final RequestLogRepository requestLogRepository;

    public UserService(UserRepository userRepository, StatisticsRepository statisticsRepository, RequestLogRepository requestLogRepository) {
        this.userRepository = userRepository;
        this.statisticsRepository = statisticsRepository;
        this.requestLogRepository = requestLogRepository;
    }

    public Map addUser(UserModel userModel) {
        String username = userModel.getUsername();
        String email = userModel.getEmail();
        return Collections.singletonMap("userId", this.userRepository.save(new User(username, email)).getUserId());
    }

    public UserModel getUserById(long id) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("user ID:" + id + " not found"));
            String username = user.getUsername();
            String email = user.getEmail();
            UserModel userModel = new UserModel(id, username, email);
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

    public Triple<List<StatisticsModel>> getUsersChangedStatusAfterTimestamp(String status, long timestamp) {
        int statusId = status.equals("OFFLINE") ? 0 : 1;
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
        long currentTime = Instant.now().getEpochSecond();
        long requestId = requestLogRepository.save(new RequestLog(currentTime, status, timestamp)).getId();
        return new Triple<>(requestId, currentTime, statisticsModels);
    }
}
