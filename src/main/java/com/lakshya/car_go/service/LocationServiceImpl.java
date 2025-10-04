package com.lakshya.car_go.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lakshya.car_go.dto.LocationCreateRequestDTO;
import com.lakshya.car_go.dto.LocationResponseDTO;
import com.lakshya.car_go.dto.LocationUpdateRequestDTO;
import com.lakshya.car_go.entity.location;
import com.lakshya.car_go.repository.locationRepository;

@Service
public class LocationServiceImpl implements LocationService {
    // define the properties
    private final locationRepository locationRepo;

    // define the constructor
    public LocationServiceImpl(locationRepository locationRepo){
        this.locationRepo = locationRepo;
    }

    // define the methord
    private LocationResponseDTO mapToResponseDTO(location location){
        // calling the responseDTO class Object
        LocationResponseDTO dto = new LocationResponseDTO();
        dto.setId(location.getId());
        dto.setName(location.getName());
        dto.setAddress(location.getAddress());
        return dto;
    }

    // define the CreateLocation() methord
    @Transactional
    @Override
    public LocationResponseDTO createLocation(LocationCreateRequestDTO dto){
        // calling the Location class Object
        location newLocation = new location();
        // define the condition
        if(locationRepo.findByNameAndAddress(dto.getName(),dto.getAddress()).isPresent()){
            throw new RuntimeException("Location already present");
        }
        newLocation.setName(dto.getName());
        newLocation.setAddress(dto.getAddress());

        location savedLocation = locationRepo.save(newLocation);

        return mapToResponseDTO(savedLocation);

    }

    // define the getByLocationId() methord
    @Override
    @Transactional(readOnly = true)
    public LocationResponseDTO getLocationId(Long id){
        // define the condition
        Optional<location> locationFound = locationRepo.findById(id);
        // define the condition
        if(locationFound.isPresent()){
            return mapToResponseDTO(locationFound.get());
        }
        throw new RuntimeException(" Location Not Found");
    }

    // define the getAllLocation() methord
    @Override
    @Transactional(readOnly = true)
    public List<LocationResponseDTO> getAllLocation(){
        List<location> locations = locationRepo.findAll();
        // create the ArrayList
        List<LocationResponseDTO> responseList =  new ArrayList<>();
        // define the for- each loop
        for(location location : locations){
            responseList.add(mapToResponseDTO(location));
        }
        return responseList;
    }

    // define the updateLocation() methord
    @Override
    @Transactional
    public LocationResponseDTO updateLocation(Long id ,LocationUpdateRequestDTO dto){
        Optional<location> locationOpt = locationRepo.findById(id);
        // define the condition
        if(locationOpt.isPresent()){
            location locationExist = locationOpt.get();
            if(dto.getName()!= null){
                locationExist.setName(dto.getName());
            }
            if(dto.getAddress()!=null){
                locationExist.setAddress(dto.getAddress());
            }

            location updtedLocation = locationRepo.save(locationExist);
            return mapToResponseDTO(updtedLocation);
        }
        throw new RuntimeException("Location Not Found");
    }

    // define the deleteLocation() methord
    @Override
    @Transactional
    public void deleteLocation(Long id){
        // define the condition
        if(locationRepo.existsById(id)){
            locationRepo.deleteById(id);
        }
        else{
            System.out.println("WARNING !! Try to delete Non-exist location");
        }
    }
}
