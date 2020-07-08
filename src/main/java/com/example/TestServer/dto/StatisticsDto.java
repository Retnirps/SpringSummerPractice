package com.example.TestServer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatisticsDto {
    private String status;
    private long timestamp;
    private String username;
    private String email;

    public StatisticsDto(String status, long timestamp) {
        this.status = status;
        this.timestamp = timestamp;
    }

    public StatisticsDto(int statusId, long timestamp, String username, String email) {
        status = statusId == 0 ? "OFFLINE" : "ONLINE";
        this.timestamp = timestamp;
        this.username = username;
        this.email = email;
    }
}
