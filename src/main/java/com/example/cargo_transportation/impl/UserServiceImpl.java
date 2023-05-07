package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.modal.dto.UserDTO;
import com.example.cargo_transportation.entity.User;
import com.example.cargo_transportation.entity.enums.ERole;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.modal.mapper.UserMapper;
import com.example.cargo_transportation.modal.payload.request.SignupRequest;
import com.example.cargo_transportation.repo.UserRepository;
import com.example.cargo_transportation.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO getCurrentUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return userMapper.toDTO(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with userId: " + userId));
    }

    @Override
    public UserDTO createUser(SignupRequest signupRequest) {
        User user = userMapper.toEntity(signupRequest);

        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        user = userRepository.save(user);
        log.info("The user: {} is saved", user.getUsername());

        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Principal principal) {
        User user = getUserByPrincipal(principal);

        user.setUsername(userDTO.getUsername());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setPatronymic(userDTO.getPatronymic());

        user = userRepository.save(user);
        log.info("The user: {} is updated", user.getUsername());

        return userMapper.toDTO(user);
    }

    @Override
    public void addRoleForUser(ERole role, Long userId) {
        User user = getUserById(userId);

        user.getRoles().add(role);

        userRepository.save(user);
        log.info("The user: {} role: {} added", user.getUsername(), role.name());
    }

    @Override
    public void deleteUser(Long userId) {
        User user = getUserById(userId);

        userRepository.delete(user);
        log.info("The user: {} is deleted", user.getUsername());
    }

    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }
}
