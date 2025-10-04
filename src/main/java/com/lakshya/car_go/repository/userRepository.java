package com.lakshya.car_go.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lakshya.car_go.entity.user;

public interface userRepository extends JpaRepository<user ,Long> {

    Optional<user> findByEmail(String email);

    
} 
