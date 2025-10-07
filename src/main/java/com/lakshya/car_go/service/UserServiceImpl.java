package com.lakshya.car_go.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors; // Added for cleaner list mapping

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lakshya.car_go.dto.UserCreateRequestDTO;
import com.lakshya.car_go.dto.UserResponseDTO;
import com.lakshya.car_go.dto.UserUpdateRequestDTO;
import com.lakshya.car_go.entity.user;
import com.lakshya.car_go.repository.userRepository;

import com.lakshya.car_go.ExceptionHandling.ResourceNotFoundException;
import com.lakshya.car_go.ExceptionHandling.DataConflictException;


@Service
public class UserServiceImpl implements UserService {
    // define the properties
    private final userRepository userRepo;

    public UserServiceImpl(userRepository userRepo){
        this.userRepo = userRepo;
    }

    // define the mapToResponseDTO() methord
    private UserResponseDTO mapToResponseDTO(user user){
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setRole(user.getRole());
        return dto;
    }

    // define the CreateUser() methord
    @Transactional
    @Override
    public UserResponseDTO createUser(UserCreateRequestDTO dto){
        
        if(userRepo.findByEmail(dto.getEmail()).isPresent()){
            throw new DataConflictException("Email '" + dto.getEmail() + "' is already in use.");
        }

        // calling the user class Onject
        user newUser = new user();
        newUser.setEmail(dto.getEmail());
        newUser.setHashedPassword(dto.getPassword());
        newUser.setRole("User"); 
        newUser.setFirstName(dto.getFirstName());
        newUser.setCreatedAt(Instant.now());

        user savedUser = userRepo.save(newUser);
        return mapToResponseDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        return userRepo.findById(id)
            .map(this::mapToResponseDTO)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepo.findAll().stream()
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO dto) {
        
        user existUser = userRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        if(dto.getEmail()!= null && !dto.getEmail().equals(existUser.getEmail())){
             if(userRepo.findByEmail(dto.getEmail()).isPresent()){
                 throw new DataConflictException("Cannot update email. Email '" + dto.getEmail() + "' is already in use by another account.");
             }
             existUser.setEmail(dto.getEmail());
        }
        
        if(dto.getFirstName()!=null){
             existUser.setFirstName(dto.getFirstName());
        } 
        
        if(dto.getPassword()!=null){
            existUser.setHashedPassword(dto.getPassword());
        } 
            
        user updatedUser = userRepo.save(existUser);
        return mapToResponseDTO(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if(!userRepo.existsById(id)){
            throw new ResourceNotFoundException("User not found with ID: " + id + " for deletion.");
        }
        userRepo.deleteById(id);
    }
}