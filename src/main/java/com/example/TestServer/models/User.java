package com.example.TestServer.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Users")
@Data public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @Column(name = "status_id")
    private int statusId;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;

    public User() {
        super();
    }

    public User(int statusId, String username, String email) {
        super();
        this.statusId = statusId;
        this.username = username;
        this.email = email;
    }
}
