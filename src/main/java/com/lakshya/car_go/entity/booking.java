package com.lakshya.car_go.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking")
@Data
@NoArgsConstructor
public class booking {
    // define the properties

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable=false)
    private user user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id",nullable = false)
    private car car;

    @Column(name = "start_date", nullable = false)
    private LocalDate starDate;

    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;

    @Column(name="booking_status",nullable = false,length=20 )
    private String bookingStatus;

    @Column(name ="total_price",nullable = false,precision = 10,scale = 2)
    private BigDecimal totalPrice;
}
