package com.lakshya.car_go.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lakshya.car_go.dto.CarCreateRequestDTO;
import com.lakshya.car_go.dto.CarResponseDTO;
import com.lakshya.car_go.dto.CarUpdateRequestDTO;
import com.lakshya.car_go.entity.car;
import com.lakshya.car_go.entity.location;
import com.lakshya.car_go.repository.carRepository;
import com.lakshya.car_go.repository.locationRepository;


@Service
public class CarServiceImpl  implements CarService{
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
        // calling the CarResponseDTO Object
        CarResponseDTO dto = new CarResponseDTO();
        dto.setId(car.getId());
        dto.setLicensePlate(car.getLicensePlate());
        dto.setDailyRate(car.getDailyRate());
        dto.setCarStatus(car.getCarStatus());
        dto.setCurrentLocationId(car.getCurrentLocation().getId());
        dto.setCurrentLocationName(car.getCurrentLocation().getName());
        return dto;
    }

    @Override
    @Transactional
    public CarResponseDTO createCar(CarCreateRequestDTO dto) {
        // define the condition
        if(carRepo.findByLicensePlate(dto.getLicensePlate()).isPresent()){
            throw new RuntimeException("Car Already Present");
        }
        // caling the car class Object
        car newCar = new car();
        newCar.setLicensePlate(dto.getLicensePlate());
        newCar.setDailyRate(dto.getDailyRate());
        newCar.setCarStatus("Available");
        newCar.setCreatedAt(Instant.now()); 

        // define the condition
        Optional<location> locationOpt = locationRepo.findById(dto.getLocationId());
        if(locationOpt.isPresent()){
            location locationExist = locationOpt.get();
            newCar.setCurrentLocation(locationExist);
            car saveCar = carRepo.save(newCar); 
            return mapToResponseDTO(saveCar);
        }
        throw new RuntimeException("LocationId is not Present ");
        
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponseDTO getCarId(String licensePlate) {
        // define the condition
        Optional<car> carOpt = carRepo.findByLicensePlate(licensePlate);
        if(carOpt.isPresent()){
            return mapToResponseDTO(carOpt.get());
        }
        throw new RuntimeException(" Car not found");
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarResponseDTO> getAllCar() {
        // define the ArrayList
        List<car> cars = carRepo.findAll();
        List<CarResponseDTO> carList = new ArrayList<>();
        // define the for-each loop
        for(car car : cars){
            carList.add(mapToResponseDTO(car));
        }
        return carList;
    }

    @Override
    @Transactional
    public CarResponseDTO updateCar(String licensePlate, CarUpdateRequestDTO dto) {
        Optional<car> carOpt = carRepo.findByLicensePlate(licensePlate);
        // define the condition
        if(carOpt.isPresent()){
            car carExist = carOpt.get();
            if(dto.getCarStatus()!= null){
                carExist.setCarStatus(dto.getCarStatus());
            }
            if(dto.getDailyRate()!=null){
                carExist.setDailyRate(dto.getDailyRate());
            }
            if(dto.getLocationId()!= null){
                Optional<location> locationOpt = locationRepo.findById(dto.getLocationId());
                if(locationOpt.isPresent()){
                    carExist.setCurrentLocation(locationOpt.get());
                }
                else{
                    throw new RuntimeException("LocationId not Found");
                }
            }
            car updateCar = carRepo.save(carExist);
            return mapToResponseDTO(updateCar);
        }
        throw new RuntimeException("Car not Found");
    }

    @Override
    @Transactional
    public void deleteCar(String licensePlate) {
        // define the condition
        if(carRepo.existsByLicensePlate(licensePlate)){
            carRepo.deleteByLicensePlate(licensePlate);
        }
        else{
            System.out.println("Car not found");
        }
    }
    
}
