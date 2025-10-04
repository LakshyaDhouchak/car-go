package com.lakshya.car_go.service;

import java.util.List;

import com.lakshya.car_go.dto.UserCreateRequestDTO;
import com.lakshya.car_go.dto.UserResponseDTO;
import com.lakshya.car_go.dto.UserUpdateRequestDTO;

public interface UserService {
    // define the properties
    
    UserResponseDTO createUser(UserCreateRequestDTO userCreateRequestDTO);
    UserResponseDTO getUserById(Long id);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO updateUser(Long id , UserUpdateRequestDTO userUpdateRequestDTO);
    void deleteUser(Long id);
}
