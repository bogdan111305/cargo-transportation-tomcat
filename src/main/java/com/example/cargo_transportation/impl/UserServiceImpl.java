package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.dto.UserDTO;
import com.example.cargo_transportation.entity.User;
import com.example.cargo_transportation.entity.enums.ERole;
import com.example.cargo_transportation.exception.UserExistException;
import com.example.cargo_transportation.payload.request.SignupRequest;
import com.example.cargo_transportation.repo.UserRepository;
import com.example.cargo_transportation.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserServiceImpl implements UserService {

    public static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

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
    public UserDTO creatUser(SignupRequest userDTO){
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving User");
            User userCreated = userRepository.save(user);
            return modelMapper.map(userCreated, UserDTO.class);
        }catch (Exception e){
            LOG.error("Error during registration. {}" + e.getMessage());
            throw new UserExistException("The user "+ user.getUsername() + " already exist.");
        }
    }
    @Override
    public UserDTO updateUser(UserDTO userDTO, Principal principal){
        User user = getUserByPrincipal(principal);

        user.setUsername(userDTO.getUsername());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());

        try {
            LOG.info("Updating User");
            User userCreated = userRepository.save(user);
            return modelMapper.map(userCreated, UserDTO.class);
        }catch (Exception e){
            LOG.error("Error during registration. {}" + e.getMessage());
            throw new UserExistException("The user "+ user.getUsername() + " already exist.");
        }
    }
    @Override
    public void deleteUser(Long userId){
        User user = getUserById(userId);

        try {
            LOG.info("Deleting User");
            userRepository.delete(user);
        }catch (Exception e){
            LOG.error("Error during registration. {}" + e.getMessage());
            throw new UserExistException("The user "+ user.getUsername() + " already exist.");
        }
    }
    @Override
    public UserDTO getCurrentUser(Principal principal){
        User user = getUserByPrincipal(principal);
        return modelMapper.map(user, UserDTO.class);
    }
    @Override
    public User getUserById(Long userId){
        return userRepository.findUserById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with username" + userId));
    }

    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username" + username));
    }

}
