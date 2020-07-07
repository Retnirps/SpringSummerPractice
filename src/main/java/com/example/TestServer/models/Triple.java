package com.example.TestServer.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Triple<T, V, C> {
    private T requestId;
    private V timestamp;
    private C usersInfo;
}
