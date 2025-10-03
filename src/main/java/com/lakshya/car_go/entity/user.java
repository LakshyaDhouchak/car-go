package com.lakshya.car_go.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="user" )
@Data
@NoArgsConstructor
public class user {
    // define the properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true , nullable = false , length = 225)
    private String email;

    @Column(name ="hashed_password",nullable = false, length = 225)
    private String hashedPassword;

    @Column(nullable = false ,length = 20)
    private String role;

    @Column(name = "first_name",nullable = false, length = 30)
    private String firstName;

    @Column(name = "created_at")
    private Instant createdAt;
}
