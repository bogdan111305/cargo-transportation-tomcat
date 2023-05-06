package com.example.cargo_transportation.modal.mapper;

import com.example.cargo_transportation.entity.User;
import com.example.cargo_transportation.modal.dto.UserDTO;
import com.example.cargo_transportation.modal.payload.request.SignupRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    UserDTO toDTO(User user);
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "patronymic", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "sessionJWT", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toEntity(UserDTO userDTO);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patronymic", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "sessionJWT", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toEntity(SignupRequest signupRequest);
}
