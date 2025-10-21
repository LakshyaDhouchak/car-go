package com.lakshya.car_go.Security;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Data Transfer Object for login responses, carrying the JWT.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor // Added AllArgsConstructor for convenience
public class LoginResponseDTO {
    
    private String jwt;
    private String email;
}
