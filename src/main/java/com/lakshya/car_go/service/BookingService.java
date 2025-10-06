package com.lakshya.car_go.service;

import java.util.List;

import com.lakshya.car_go.dto.BookingCreateRequestDTO;
import com.lakshya.car_go.dto.BookingResponseDTO;
import com.lakshya.car_go.dto.BookingUpdateRequestDTO;

public interface BookingService {
    // define the properties

    BookingResponseDTO createBooking(BookingCreateRequestDTO dto);
    BookingResponseDTO findBookingById(Long id);
    List<BookingResponseDTO> findAllBooking();
    BookingResponseDTO updateBooking(Long id, BookingUpdateRequestDTO dto);
    void deleteBooking(Long id);
}
