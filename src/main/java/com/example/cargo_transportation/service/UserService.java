package com.example.cargo_transportation.service;

import com.example.cargo_transportation.entity.enums.ERole;
import com.example.cargo_transportation.modal.dto.UserDTO;
import com.example.cargo_transportation.entity.User;
import com.example.cargo_transportation.modal.payload.request.SignupRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

public interface UserService {
    UserDTO getCurrentUser(Principal principal);

    UserDTO createUser(SignupRequest userDTO);

    UserDTO updateUser(UserDTO userDTO, Principal principal);

    void addRoleForUser(ERole role,Long userId);

    void deleteUser(Long userId);

    User getUserById(Long userId);

    User getUserByPrincipal(Principal principal);
}
