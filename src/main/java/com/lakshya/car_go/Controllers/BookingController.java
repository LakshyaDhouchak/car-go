package com.lakshya.car_go.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lakshya.car_go.dto.BookingCreateRequestDTO;
import com.lakshya.car_go.dto.BookingResponseDTO;
import com.lakshya.car_go.dto.BookingUpdateRequestDTO;
import com.lakshya.car_go.service.BookingServiceImpl;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    // define the properties
    private final BookingServiceImpl bookingService;

    // define the constructor
    public BookingController(BookingServiceImpl bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingCreateRequestDTO dto){
        BookingResponseDTO newBooking  = bookingService.createBooking(dto);
        return new ResponseEntity<>(newBooking,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BookingResponseDTO> findById(@PathVariable Long id){
        BookingResponseDTO booking = bookingService.findBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<BookingResponseDTO>> getAllBooking(){
        List<BookingResponseDTO> bookings = bookingService.findAllBooking();
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> updateBooking(@PathVariable Long id , @RequestBody BookingUpdateRequestDTO dto){
        BookingResponseDTO updatedBooking = bookingService.updateBooking(id,dto);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
