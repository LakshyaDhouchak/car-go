package com.lakshya.car_go.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lakshya.car_go.dto.UserCreateRequestDTO;
import com.lakshya.car_go.dto.UserResponseDTO;
import com.lakshya.car_go.dto.UserUpdateRequestDTO;
import com.lakshya.car_go.entity.user;
import com.lakshya.car_go.repository.userRepository;

@Service
public class UserServiceImpl implements UserService {
    // define the properties
    private final userRepository userRepo;

    public UserServiceImpl(userRepository userRepo){
        this.userRepo = userRepo;
    }

    // define the CreateUser() methord
    @Transactional
    @Override
    public UserResponseDTO createUser(UserCreateRequestDTO dto){
        // define the condition
        if(userRepo.findByEmail(dto.getEmail()).isPresent()){
            throw new RuntimeException("Email already in use");
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

    // define the mapToResponseDTO() methord
    private UserResponseDTO mapToResponseDTO(user user){
        // calling the UserResponseDto
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setRole(user.getRole());
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        // define the condition
        Optional<user> userFound = userRepo.findById(id);
        if(userFound.isPresent()){
            return mapToResponseDTO(userFound.get());
        }
        throw new RuntimeException("User Not found");
        
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        List<user> users = userRepo.findAll();
        // define the  properties
        List<UserResponseDTO> responseList = new ArrayList<>();
        for(user userEntity : users){
            responseList.add(mapToResponseDTO(userEntity));
        }
        return responseList;
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO dto) {
        // define the properties
        Optional<user> userOpt = userRepo.findById(id);

        // define the condition
        if(userOpt.isPresent()){
            user existUser = userOpt.get();
            if(dto.getEmail()!= null){
                existUser.setEmail(dto.getEmail());
            }
            if(dto.getPassword()!=null){
                existUser.setFirstName(dto.getPassword());
            }  
            if(dto.getPassword()!=null){
                existUser.setHashedPassword(dto.getPassword());
            } 
            
            user updatedUser = userRepo.save(existUser);
            return mapToResponseDTO(updatedUser);
        }
        throw new RuntimeException("User Not Found");
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        // define the condition
        if(userRepo.existsById(id)){
            userRepo.deleteById(id);
        }
        else{
            System.out.println("WARNING !! Try to delete Non-exist user");
        }
    }

}
