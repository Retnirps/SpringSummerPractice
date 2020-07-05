package com.example.TestServer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "status_id")
    private int statusId;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;

    public User(int statusId, String username, String email) {
        super();
        this.statusId = statusId;
        this.username = username;
        this.email = email;
    }

    public User(String username, String email) {
        super();
        this.username = username;
        this.email = email;
    }
}
