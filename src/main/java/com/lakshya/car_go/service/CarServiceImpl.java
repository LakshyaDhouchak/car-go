package com.lakshya.car_go.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors; 

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lakshya.car_go.ExceptionHandling.ResourceNotFoundException;
import com.lakshya.car_go.ExceptionHandling.DataConflictException;
import com.lakshya.car_go.ExceptionHandling.InvalidInputException;

import com.lakshya.car_go.dto.CarCreateRequestDTO;
import com.lakshya.car_go.dto.CarResponseDTO;
import com.lakshya.car_go.dto.CarUpdateRequestDTO;
import com.lakshya.car_go.entity.car;
import com.lakshya.car_go.entity.location;
import com.lakshya.car_go.repository.carRepository;
import com.lakshya.car_go.repository.locationRepository;


@Service
public class CarServiceImpl implements CarService{
    // define the properties
    private final carRepository carRepo;
    private final locationRepository locationRepo;

    // define the constructor
    public CarServiceImpl(carRepository carRepo , locationRepository locationRepo){
        this.carRepo = carRepo;
        this.locationRepo = locationRepo;
    }

    // define the methord
    private CarResponseDTO mapToResponseDTO(car car){
        CarResponseDTO dto = new CarResponseDTO();
        dto.setId(car.getId());
        dto.setLicensePlate(car.getLicensePlate());
        dto.setDailyRate(car.getDailyRate());
        dto.setCarStatus(car.getCarStatus());
        if (car.getCurrentLocation() != null) {
            dto.setCurrentLocationId(car.getCurrentLocation().getId());
            dto.setCurrentLocationName(car.getCurrentLocation().getName());
        }
        return dto;
    }

    @Override
    @Transactional
    public CarResponseDTO createCar(CarCreateRequestDTO dto) {
        
        
        if(carRepo.findByLicensePlate(dto.getLicensePlate()).isPresent()){
            throw new DataConflictException("Car with license plate '" + dto.getLicensePlate() + "' already exists.");
        }
        
        
        location locationExist = locationRepo.findById(dto.getLocationId())
            .orElseThrow(() -> new ResourceNotFoundException("Location not found with ID: " + dto.getLocationId()));
        
        if (dto.getDailyRate() == null || dto.getDailyRate().doubleValue() <= 0) {
            throw new InvalidInputException("Daily rate must be a positive value.");
        }

        // Caling the car class Object
        car newCar = new car();
        newCar.setLicensePlate(dto.getLicensePlate());
        newCar.setDailyRate(dto.getDailyRate());
        newCar.setCarStatus("Available"); 
        newCar.setCreatedAt(Instant.now()); 
        newCar.setCurrentLocation(locationExist);
        
        car saveCar = carRepo.save(newCar); 
        return mapToResponseDTO(saveCar);
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponseDTO getCarId(String licensePlate) {
        car carExist = carRepo.findByLicensePlate(licensePlate)
            .orElseThrow(() -> new ResourceNotFoundException("Car not found with license plate: " + licensePlate));
            
        return mapToResponseDTO(carExist);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarResponseDTO> getAllCar() {
        return carRepo.findAll().stream()
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CarResponseDTO updateCar(String licensePlate, CarUpdateRequestDTO dto) {
        
        car carExist = carRepo.findByLicensePlate(licensePlate)
            .orElseThrow(() -> new ResourceNotFoundException("Car not found with license plate: " + licensePlate));
            
        if(dto.getCarStatus()!= null){
            carExist.setCarStatus(dto.getCarStatus());
        }
        
        if(dto.getDailyRate()!=null){
            if (dto.getDailyRate().doubleValue() <= 0) {
                throw new InvalidInputException("Daily rate must be a positive value for update.");
            }
            carExist.setDailyRate(dto.getDailyRate());
        }
        
        if(dto.getLocationId()!= null){
            location locationExist = locationRepo.findById(dto.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with ID: " + dto.getLocationId()));
            
            carExist.setCurrentLocation(locationExist);
        }
        
        car updateCar = carRepo.save(carExist);
        return mapToResponseDTO(updateCar);
    }

    @Override
    @Transactional
    public void deleteCar(String licensePlate) {
        
        if(carRepo.existsByLicensePlate(licensePlate)){
            carRepo.deleteByLicensePlate(licensePlate);
        }
        else{
            throw new ResourceNotFoundException("Car not found with license plate: " + licensePlate + " for deletion.");
        }
    }
}