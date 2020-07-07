package com.example.TestServer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "request_log")
@NoArgsConstructor
@Data
public class RequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long timestamp;
    private String status;
    @Column(name = "after_time")
    private long afterTime;

    public RequestLog(long timestamp, String status, long afterTime) {
        this.timestamp = timestamp;
        this.status = status;
        this.afterTime = afterTime;
    }
}
