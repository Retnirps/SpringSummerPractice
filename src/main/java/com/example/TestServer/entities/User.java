package com.example.TestServer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;
    @Column(name = "status_id")
    private int statusId;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;

    public User(String username, String email) {
        super();
        this.username = username;
        this.email = email;
    }
}
