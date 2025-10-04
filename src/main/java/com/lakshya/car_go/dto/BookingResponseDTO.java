package com.lakshya.car_go.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingResponseDTO {
    // define the properties

    private Long id;
    private Long userId;
    private Long carId;
    private String userEmail;
    private String carLicensePlate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String bookingStatus;
    private BigDecimal totalPrice;
    
}
