package com.example.TestServer.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsModel {
    private String username;
    private String email;
    private int statusId;
    private long timestamp;
}
