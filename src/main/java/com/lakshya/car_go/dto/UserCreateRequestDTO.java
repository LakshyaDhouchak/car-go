package com.lakshya.car_go.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateRequestDTO {
    // define the properties

    @NotBlank(message = "Email is Required")
    @Email(message = "Email must be valid")
    @Size(max = 225,message="Email length exceended")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message =" Password must be atleast 8 character")
    private String password;

    @NotBlank(message=" First Name is required")
    @Size(max = 30,message = "first name can't be greater than 30 character")
    private String firstName;
}
