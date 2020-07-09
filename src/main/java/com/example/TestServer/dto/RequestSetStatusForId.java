package com.example.TestServer.dto;

import com.example.TestServer.models.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSetStatusForId {
    private long userId;
    private String status;
}
