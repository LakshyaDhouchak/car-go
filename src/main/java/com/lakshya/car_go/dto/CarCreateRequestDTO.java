package com.lakshya.car_go.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarCreateRequestDTO {
    // define the properties

    @NotBlank(message ="License plate is required")
    private String licensePlate;

    @NotNull(message = "DailyRate is required")
    @DecimalMin(value = "0.01", message = "Daily rate must be greater than 0")
    private BigDecimal  dailyRate;

    @NotNull(message = "CurrentLocation id is required")
    private Long locationId;
}
