package com.lakshya.car_go.entity;

import java.math.BigDecimal;
import java.time.Instant;
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
@Table(name ="car")
@Data
@NoArgsConstructor
public class car {
    // define the properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", nullable= false, unique = true,length = 20)
    private String licensePlate;

    @Column(name = "daily_rate",nullable = false,precision = 10, scale = 2)
    private BigDecimal dailyRate;

    @Column(name = "Car_status",nullable=false,length = 12)
    private String carStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_location_id",nullable= false)
    private location currentLocation;

    @Column(name ="created_at")
    private Instant createdAt;
}
