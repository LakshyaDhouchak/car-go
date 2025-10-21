package com.lakshya.car_go.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lakshya.car_go.repository.userRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    // define the properties
    private final  userRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {
        return userRepo.findByEmail(Email).orElseThrow(()-> new UsernameNotFoundException("User not found with email: \" + username"));
    }
    
}
