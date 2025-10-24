package com.lakshya.car_go.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    // define the properties
    @Autowired
    private KafkaTemplate<String, BookingConfirmationEvent> kafkaTemplate;
    private static final String TOPIC = "Booking-Confirmation";

    // define the methord
    public void sendBookingConfirmation(BookingConfirmationEvent event){
        String key = event.getBookingId();
        kafkaTemplate.send(TOPIC,key,event);
        System.out.println("EVENT PUBLISHED to Kafka: Booking ID"+ key );
    }

}
