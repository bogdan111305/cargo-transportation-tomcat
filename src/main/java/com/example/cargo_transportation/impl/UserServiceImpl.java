package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.dto.UserDTO;
import com.example.cargo_transportation.entity.User;
import com.example.cargo_transportation.entity.enums.ERole;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.payload.request.SignupRequest;
import com.example.cargo_transportation.repo.UserRepository;
import com.example.cargo_transportation.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO getCurrentUser(Principal principal){
        User user = getUserByPrincipal(principal);
        return modelMapper.map(user, UserDTO.class);
    }
    @Override
    public User getUserById(Long userId){
        return userRepository.findUserById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with username: " + userId));
    }

    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

    @Override
    public UserDTO createUser(SignupRequest userDTO){
        User user = modelMapper.map(userDTO, User.class);

        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        user = userRepository.save(user);
        log.info("The user: {} is saved" + user.getUsername());

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Principal principal){
        User user = getUserByPrincipal(principal);

        user.setUsername(userDTO.getUsername());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());

        user = userRepository.save(user);
        log.info("The user: {} is updated" + user.getUsername());

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public void deleteUser(Long userId){
        User user = getUserById(userId);

        userRepository.delete(user);
        log.info("The user: {} is deleted" + user.getUsername());
    }
}
