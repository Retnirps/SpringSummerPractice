package com.example.TestServer.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Triple<T> {
    private long requestId;
    private long timestamp;
    private T usersInfo;
}
