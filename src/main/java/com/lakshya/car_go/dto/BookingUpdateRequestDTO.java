package com.lakshya.car_go.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingUpdateRequestDTO {
    // define the properties

    @Future(message = "New start date must be in the future")
    private LocalDate startDate;

    @Future(message = "new end date must be in future")
    private LocalDate endDate;

    @Size(max = 20, message=" Booking status length limit exceeded")
    private String bookingStatus;
}
