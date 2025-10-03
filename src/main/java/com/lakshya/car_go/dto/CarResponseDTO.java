package com.lakshya.car_go.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarResponseDTO {
    // define the properties

    private Long id;
    private String LicensePlate;
    private BigDecimal dailyRate;
    private String carStatus;
    private Long currentLocationId;
    private String currentLocationName;
}
