package com.example.TestServer.models;

import lombok.Data;

import javax.persistence.*;

@Table(name = "Statistics")
@Data
public class Statistics {
    private int user_id;
    private int status_id;
    private long change_time;
}
