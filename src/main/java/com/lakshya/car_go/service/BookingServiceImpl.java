package com.lakshya.car_go.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; // Added for cleaner list mapping

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lakshya.car_go.dto.BookingCreateRequestDTO;
import com.lakshya.car_go.dto.BookingResponseDTO;
import com.lakshya.car_go.dto.BookingUpdateRequestDTO;
import com.lakshya.car_go.entity.booking;
import com.lakshya.car_go.entity.car;
import com.lakshya.car_go.entity.user;
import com.lakshya.car_go.repository.bookingRepository;
import com.lakshya.car_go.repository.carRepository;
import com.lakshya.car_go.repository.userRepository;


@Service
public class BookingServiceImpl implements BookingService{
    
    private final bookingRepository bookingRepo;
    private final userRepository userRepo;
    private final carRepository carRepo;
    
    public BookingServiceImpl(bookingRepository bookingRepo,userRepository userRepo, carRepository carRepo){
        this.bookingRepo = bookingRepo;
        this.userRepo = userRepo;
        this.carRepo = carRepo;
    }

    private BookingResponseDTO mapToResponseDTO(booking booking){
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(booking.getId());
        dto.setBookingStatus(booking.getBookingStatus());
        dto.setStartDate(booking.getStartDate());
        dto.setEndDate(booking.getEndDate());
        // Null checks might be needed here if relationships aren't guaranteed
        if (booking.getCar() != null) {
            dto.setCarId(booking.getCar().getId());
            dto.setCarLicensePlate(booking.getCar().getLicensePlate());
        }
        if (booking.getUser() != null) {
            dto.setUserEmail(booking.getUser().getEmail());
            dto.setUserId(booking.getUser().getId());
        }
        dto.setTotalPrice(booking.getTotalPrice());
        return dto;
    }

    private long calculateDurationDays(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Start date cannot be after end date.");
        }
        
        // Duration is inclusive of the start and end day (+1)
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1; 

        if (days <= 0) {
             throw new RuntimeException("Booking duration must be at least one day.");
        }
        return days;
    }

    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingCreateRequestDTO dto) {
        
        // 1. Find User by UserId (FIXED: Used dto.getUserId())
        user userFound = userRepo.findById(dto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getUserId()));

        // 2. Find Car by CarId and check availability
        car carFound = carRepo.findById(dto.getCarId())
            .orElseThrow(() -> new RuntimeException("Car not found with ID: " + dto.getCarId()));
        
        if (!"Available".equalsIgnoreCase(carFound.getCarStatus())) {
             throw new RuntimeException("Car is not available for booking.");
        }

        // 3. Calculate price (FIXED: Used carFound.getDailyRate())
        long days = calculateDurationDays(dto.getStartDate(), dto.getEndDate());
        BigDecimal totalPrice = carFound.getDailyRate().multiply(BigDecimal.valueOf(days));
        
        // 4. Create and Save Booking
        booking newBooking = new booking();
        newBooking.setStartDate(dto.getStartDate());
        newBooking.setEndDate(dto.getEndDate());
        newBooking.setTotalPrice(totalPrice);
        newBooking.setCar(carFound);
        newBooking.setUser(userFound);
        newBooking.setBookingStatus("Booked"); // Consider using an Enum for status
        
        // Optional: Update car status
        // carFound.setCarStatus("Reserved");
        // carRepo.save(carFound);
        
        booking savedBooking = bookingRepo.save(newBooking);
        return mapToResponseDTO(savedBooking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponseDTO findBookingById(Long id) {
        booking bookingExist = bookingRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
            
        return mapToResponseDTO(bookingExist);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDTO> findAllBooking() {
        return bookingRepo.findAll().stream()
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingResponseDTO updateBooking(Long id, BookingUpdateRequestDTO dto) {
        
        // Retrieve booking using orElseThrow for cleaner code
        booking existingBooking = bookingRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));

        // Determine new start/end dates, defaulting to existing if DTO is null
        LocalDate newStartDate = dto.getStartDate() != null ? dto.getStartDate() : existingBooking.getStartDate();
        LocalDate newEndDate = dto.getEndDate() != null ? dto.getEndDate() : existingBooking.getEndDate();

        // 1. Update Booking fields
        existingBooking.setStartDate(newStartDate);
        existingBooking.setEndDate(newEndDate);
        
        if (dto.getBookingStatus() != null) {
            existingBooking.setBookingStatus(dto.getBookingStatus());
        }

        // 2. Recalculate TotalPrice based on new dates (FIXED: Used existing car's rate)
        long days = calculateDurationDays(newStartDate, newEndDate);
        BigDecimal carDailyRate = existingBooking.getCar().getDailyRate(); 
        
        BigDecimal totalPrice = carDailyRate.multiply(BigDecimal.valueOf(days));
        existingBooking.setTotalPrice(totalPrice);

        booking savedBooking = bookingRepo.save(existingBooking);
        return mapToResponseDTO(savedBooking);
    }

    @Override
    @Transactional
    public void deleteBooking(Long id) {
        if (bookingRepo.existsById(id)) {
            bookingRepo.deleteById(id);
        } else {
            // Throwing an exception is often better than a print statement in a Service layer
            throw new RuntimeException("Booking not found with ID: " + id + " for deletion.");
        }
    }
}
