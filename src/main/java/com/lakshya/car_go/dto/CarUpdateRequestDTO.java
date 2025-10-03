package com.lakshya.car_go.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarUpdateRequestDTO {
    // define the properties

    @DecimalMin(value="0.01",message = "Daily Rate must be greater than 0")
    private BigDecimal dailyRate;

    @Size(max = 12, message = "carStatus must be greater than 12 characters")
    private String carStatus;

    private Long locationId;


}
