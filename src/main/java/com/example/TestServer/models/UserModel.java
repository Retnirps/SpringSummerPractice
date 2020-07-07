package com.example.TestServer.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserModel {
    private long id;
    private String username;
    private int statusId;
    private int oldStatusId;
    private String email;

    public UserModel(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UserModel(long id, int oldStatusId, int statusId) {
        this.id = id;
        this.oldStatusId = oldStatusId;
        this.statusId = statusId;
    }
}
