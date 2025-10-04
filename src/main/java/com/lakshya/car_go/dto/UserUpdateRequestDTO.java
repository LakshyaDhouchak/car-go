package com.lakshya.car_go.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateRequestDTO {
    // define the properties

    @Email(message = "Email must be valid")
    @Size(max = 225, message = "Email length limit exceeded")
    private String email;

    
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password; 
    
    @Size(max = 30, message = "First name cannot exceed 30 characters")
    private String firstName;
}
