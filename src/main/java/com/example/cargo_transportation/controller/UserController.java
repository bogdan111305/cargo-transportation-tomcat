package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.dto.UserDTO;
import com.example.cargo_transportation.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public UserDTO getUserProfile(Principal principal){
        return userService.getCurrentUser(principal);
    }

    @PutMapping
    public UserDTO updateUser(@Valid @RequestBody UserDTO user, Principal principal){
        return userService.updateUser(user, principal);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("userId")Long userId){
        userService.deleteUser(userId);
    }
}
