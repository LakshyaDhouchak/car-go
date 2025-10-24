package com.lakshya.car_go.Kafka;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingConfirmationEvent {
    // define the properties
    
    private String bookingId;
    private String userEmail;
    private String bookingSummary;
    private Instant timeStamp;
}
