package com.example.TestServer.entities;

import com.example.TestServer.models.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class Status {
    @Id
    private int statusId;
    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;
}
