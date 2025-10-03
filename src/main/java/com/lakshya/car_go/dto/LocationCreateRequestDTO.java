package com.lakshya.car_go.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationCreateRequestDTO {
    // define the properties

    @NotBlank(message = "Location name is required")
    @Size(max = 30,message = "Location name can't be greater than 30 character")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(max = 100, message = " Address can't be greater than 100 character")
    private String Address;

}
