package com.example.cargo_transportation.service;

import com.example.cargo_transportation.dto.UserDTO;
import com.example.cargo_transportation.entity.User;
import com.example.cargo_transportation.payload.request.SignupRequest;

import java.security.Principal;

public interface UserService {
    UserDTO creatUser(SignupRequest userDTO);

    UserDTO updateUser(UserDTO userDTO, Principal principal);

    UserDTO getCurrentUser(Principal principal);

    User getUserById(Long userId);

    void deleteUser(Long userId);
}
