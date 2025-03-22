package com.bright.srs.service;

import com.bright.srs.dto.request.UserRequestDto;
import com.bright.srs.dto.response.UserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserResponseDto> createUser(UserRequestDto userRequestDto);
    Optional<UserResponseDto> updateUser(String username, UserRequestDto userRequestDto);
    void deleteUser(String username);
    List<UserResponseDto> findAllUsers();
    Optional<UserResponseDto> findUserByUsername(String username);
}
