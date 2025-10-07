package com.lakshya.car_go.service;

import java.util.List;
import java.util.stream.Collectors; // Using stream for cleaner list mapping

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lakshya.car_go.dto.LocationCreateRequestDTO;
import com.lakshya.car_go.dto.LocationResponseDTO;
import com.lakshya.car_go.dto.LocationUpdateRequestDTO;
import com.lakshya.car_go.entity.location;
import com.lakshya.car_go.repository.locationRepository;


import com.lakshya.car_go.ExceptionHandling.ResourceNotFoundException;
import com.lakshya.car_go.ExceptionHandling.DataConflictException;


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
    
        if(locationRepo.findByNameAndAddress(dto.getName(),dto.getAddress()).isPresent()){
            throw new DataConflictException("Location with name '" + dto.getName() + 
                                            "' and address '" + dto.getAddress() + "' already exists.");
        }
        
        location newLocation = new location();
        newLocation.setName(dto.getName());
        newLocation.setAddress(dto.getAddress());

        location savedLocation = locationRepo.save(newLocation);

        return mapToResponseDTO(savedLocation);
    }

    // define the getByLocationId() methord
    @Override
    @Transactional(readOnly = true)
    public LocationResponseDTO getLocationId(Long id){
        return locationRepo.findById(id)
            .map(this::mapToResponseDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Location not found with ID: " + id));
    }

    // define the getAllLocation() methord
    @Override
    @Transactional(readOnly = true)
    public List<LocationResponseDTO> getAllLocation(){
        
        return locationRepo.findAll().stream()
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());
    }

    // define the updateLocation() methord
    @Override
    @Transactional
    public LocationResponseDTO updateLocation(Long id ,LocationUpdateRequestDTO dto){
        
        location locationExist = locationRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Location not found with ID: " + id));
            
        
        if(dto.getName()!= null){
            locationExist.setName(dto.getName());
        }
        if(dto.getAddress()!=null){
            locationExist.setAddress(dto.getAddress());
        }

        location updtedLocation = locationRepo.save(locationExist);
        return mapToResponseDTO(updtedLocation);
    }

    // define the deleteLocation() methord
    @Override
    @Transactional
    public void deleteLocation(Long id){
        if(!locationRepo.existsById(id)){
            throw new ResourceNotFoundException("Location not found with ID: " + id + " for deletion.");
        }
        locationRepo.deleteById(id);
    }
}