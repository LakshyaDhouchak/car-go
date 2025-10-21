package com.lakshya.car_go.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lakshya.car_go.entity.location;

public interface locationRepository  extends JpaRepository<location , Long>{
    Optional<location> findByNameAndAddress(String name, String address);
}   
