package com.lakshya.car_go.service;

import java.util.List;

import com.lakshya.car_go.dto.CarCreateRequestDTO;
import com.lakshya.car_go.dto.CarResponseDTO;
import com.lakshya.car_go.dto.CarUpdateRequestDTO;

public interface CarService {
    // define the properties

    CarResponseDTO createCar(CarCreateRequestDTO dto);
    CarResponseDTO getCarId(String LicensePlate);
    List<CarResponseDTO> getAllCar();
    CarResponseDTO updateCar(String licensePlate, CarUpdateRequestDTO dto); 
    void deleteCar(String licensePlate);
}
