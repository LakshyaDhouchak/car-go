package com.lakshya.car_go.Security;

import com.lakshya.car_go.Security.CustomUserDetailService;
import com.lakshya.car_go.Security.JwtService;
import com.lakshya.car_go.Security.LoginRequestDTO;
import com.lakshya.car_go.Security.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        
        // 1. Authenticate the user credentials using the AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        // 2. Load UserDetails after successful authentication
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        
        // 3. Generate JWT
        final String jwt = jwtService.generateToken(userDetails);
        
        // 4. Return the JWT to the client
        return ResponseEntity.ok(new LoginResponseDTO(jwt, userDetails.getUsername()));
    }
}
