package com.lakshya.car_go.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lakshya.car_go.dto.LocationCreateRequestDTO;
import com.lakshya.car_go.dto.LocationResponseDTO;
import com.lakshya.car_go.dto.LocationUpdateRequestDTO;
import com.lakshya.car_go.service.LocationServiceImpl;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    // define the properties
    private final LocationServiceImpl locationService;

    // define the constructor
    public LocationController(LocationServiceImpl locationService){
        this.locationService = locationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> getLocationById(@PathVariable Long id){
        LocationResponseDTO location = locationService.getLocationId(id);
        return ResponseEntity.ok(location);
    }

    @PostMapping
    public ResponseEntity<LocationResponseDTO> CreateLocation(@RequestBody LocationCreateRequestDTO dto){
        LocationResponseDTO newLocation = locationService.createLocation(dto);
        return new ResponseEntity<>(newLocation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LocationResponseDTO>> getAllLocation(){
        List<LocationResponseDTO> locations = locationService.getAllLocation();
        return ResponseEntity.ok(locations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> updateLocation(@PathVariable Long id , @RequestBody LocationUpdateRequestDTO dto){
        LocationResponseDTO locationUpdated = locationService.updateLocation(id , dto);
        return ResponseEntity.ok(locationUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id){
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
