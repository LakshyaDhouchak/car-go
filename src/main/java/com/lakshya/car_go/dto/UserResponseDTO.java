package com.lakshya.car_go.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDTO {
    // define the properties

    private Long id;
    private String email;
    private String Role;
    private String firstName;
}
