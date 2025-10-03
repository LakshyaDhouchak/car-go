package com.lakshya.car_go.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lakshya.car_go.entity.car;

public interface carRepository  extends JpaRepository<car,Long>{
    
}
