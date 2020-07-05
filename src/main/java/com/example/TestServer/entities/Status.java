package com.example.TestServer.entities;

import com.example.TestServer.models.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "status")
@Data
public class Status {
    @Id
    @Column(name = "status_id")
    private int statusId;
    @Column(name = "status")
    private StatusEnum statusEnum;
}
