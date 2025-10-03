package com.lakshya.car_go.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationUpdateRequestDTO {
    // define the properties

    @Size(max = 30,message ="Location can't be more than 30 character")
    private String name;

    @Size(max = 100 , message = "Address can't be more than 100 character")
    private String Address;
}
