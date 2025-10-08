package com.lakshya.car_go.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lakshya.car_go.entity.booking;

public interface bookingRepository extends JpaRepository<booking,Long> {
    
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END "+
           "FROM booking b"+
           "WHERE b.car.id = :carId" +
           "AND (:startDate <= b.endDate AND :endDate >= b.startDate)")
    boolean existOverlapping(@Param("carId") Long carId, @Param("startDate") LocalDate startDate ,@Param("endDate") LocalDate endDate);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END " +
           "FROM booking b " +
           "WHERE b.car.id = :carId " +
           "AND b.id != :bookingIdToExclude " + 
           "AND b.bookingStatus IN ('Booked', 'Reserved', 'In Use') " +
           "AND (:startDate <= b.endDate AND :endDate >= b.startDate)")
    boolean existsOverlappingBookingExcludingId(
        @Param("carId") Long carId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("bookingIdToExclude") Long bookingIdToExclude 
    );
}
