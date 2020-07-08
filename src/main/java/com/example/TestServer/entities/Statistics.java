package com.example.TestServer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "statistics")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statistics_id")
    private long statisticsId;
    @OneToOne
    @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id")
    private User user;
    @Column(name = "status_id")
    private int statusId;
    @Column(name = "change_time")
    private long changeTime;

    public Statistics(User user, int statusId, long changeTime) {
        this.user = user;
        this.statusId = statusId;
        this.changeTime = changeTime;
    }
}