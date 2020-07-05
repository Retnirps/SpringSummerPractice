package com.example.TestServer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String username;
    private int statusId;
    private String email;

    public UserDto(String username, int statusId, String email) {
        this.username = username;
        this.statusId = statusId;
        this.email = email;
    }
}
