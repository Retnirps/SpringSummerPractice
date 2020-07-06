package com.example.TestServer.dto;

import com.example.TestServer.models.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserStatusDto {
    private int userId;
    private String status;
    private String oldStatus;


    public UserStatusDto(int userId, String status) {
        this.userId = userId;
        this.status = status;
    }

    public UserStatusDto(int userId, int oldStatusId, int statusId) {
        this.userId = userId;
        oldStatus = StatusEnum.values()[oldStatusId].name();
        status = StatusEnum.values()[statusId].name();
    }
}
