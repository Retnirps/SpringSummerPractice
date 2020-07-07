package com.example.TestServer.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair<T, V> {
        T requestId;
        V usersInfo;
}
