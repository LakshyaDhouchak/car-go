package com.lakshya.car_go.Security;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class LoginRequestDTO {
    // define the properties
    private String Email;
    private String password;
}
