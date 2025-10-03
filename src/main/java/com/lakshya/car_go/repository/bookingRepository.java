package com.lakshya.car_go.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lakshya.car_go.entity.booking;

public interface bookingRepository extends JpaRepository<booking,Long> {
    
}
