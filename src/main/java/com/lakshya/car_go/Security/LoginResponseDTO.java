package com.lakshya.car_go.Security;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class LoginResponseDTO {
    // define the properties
    private String jwt;
    private String email;
}
