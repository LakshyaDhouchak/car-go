package com.lakshya.car_go.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lakshya.car_go.dto.CarCreateRequestDTO;
import com.lakshya.car_go.dto.CarResponseDTO;
import com.lakshya.car_go.dto.CarUpdateRequestDTO;
import com.lakshya.car_go.service.CarServiceImpl;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/cars")
public class CarController {
    // define the properties
    private final CarServiceImpl carService;

    // define the constructor
    public CarController(CarServiceImpl carService){
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<CarResponseDTO> createCar(@RequestBody CarCreateRequestDTO dto){
        CarResponseDTO newCar = carService.createCar(dto);
        return new ResponseEntity<>(newCar,HttpStatus.CREATED);
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<CarResponseDTO> getCarByLicensePlate(@PathVariable String licensePlate){
        CarResponseDTO car = carService.getCarId(licensePlate);
        return ResponseEntity.ok(car);
    }

    @GetMapping
    public ResponseEntity<List<CarResponseDTO>> getAllCars(){
        List<CarResponseDTO> cars = carService.getAllCar();
        return ResponseEntity.ok(cars);
    }

    @PutMapping("/{licensePate}")
    public ResponseEntity<CarResponseDTO> updateCar(@PathVariable String licensePlate , @RequestBody CarUpdateRequestDTO dto){
        CarResponseDTO carUpdated = carService.updateCar(licensePlate, dto);
        return ResponseEntity.ok(carUpdated);
    } 

    @DeleteMapping("/{licensePlate}")
    public ResponseEntity<Void> deleteCar(@PathVariable String licensePate){
        carService.deleteCar(licensePate);
        return ResponseEntity.noContent().build();
    }
    
}
