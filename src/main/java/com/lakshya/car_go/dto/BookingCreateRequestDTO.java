package com.lakshya.car_go.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingCreateRequestDTO {
    // define the properties

    @NotNull(message= "User ID is required")
    private Long userId;

    @NotNull(message = "Car Id is required")
    private Long carId;

    @NotNull(message = "Start Date is required")
    @FutureOrPresent(message = "Start date can't be in the past")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

}
