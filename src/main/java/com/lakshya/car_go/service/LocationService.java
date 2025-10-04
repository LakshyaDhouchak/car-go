package com.lakshya.car_go.service;

import java.util.List;

import com.lakshya.car_go.dto.LocationCreateRequestDTO;
import com.lakshya.car_go.dto.LocationResponseDTO;
import com.lakshya.car_go.dto.LocationUpdateRequestDTO;

public interface LocationService {
    // define the properties

    LocationResponseDTO createLocation(LocationCreateRequestDTO dto);
    LocationResponseDTO getLocationId(Long id);
    List<LocationResponseDTO> getAllLocation();
    LocationResponseDTO updateLocation(Long id , LocationUpdateRequestDTO dto);
    void deleteLocation(Long id);
}
