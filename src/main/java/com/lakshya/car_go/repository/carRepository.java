package com.lakshya.car_go.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lakshya.car_go.entity.car;

public interface carRepository  extends JpaRepository<car,Long>{
    // define the properties
    Optional<car> findByLicensePlate(String licensePlate);

    boolean existsByLicensePlate(String licensePlate);

    void deleteByLicensePlate(String licensePlate);
}
