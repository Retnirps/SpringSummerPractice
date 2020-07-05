package com.example.TestServer.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserModel {
    private int id;
    private String username;
    private int statusId;
    private String email;

    public UserModel(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UserModel(String username, int statusId, String email) {
        this.username = username;
        this.statusId = statusId;
        this.email = email;
    }
}
