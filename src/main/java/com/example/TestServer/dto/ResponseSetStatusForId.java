package com.example.TestServer.dto;

import com.example.TestServer.models.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseSetStatusForId {
    private long userId;
    private String status;
    private String oldStatus;

    public ResponseSetStatusForId(long userId, int oldStatusId, int statusId) {
        this.userId = userId;
        oldStatus = StatusEnum.values()[oldStatusId].name();
        status = StatusEnum.values()[statusId].name();
    }
}
