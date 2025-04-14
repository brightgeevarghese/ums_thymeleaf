package com.bright.fsd.service;

import com.bright.fsd.dto.request.UserRequestDto;
import com.bright.fsd.dto.response.UserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserResponseDto> createUser(UserRequestDto userRequestDto);
    Optional<UserResponseDto> updateUser(String username, UserRequestDto userRequestDto);
    void deleteUser(String username);
    List<UserResponseDto> findAllUsers();
    Optional<UserResponseDto> findUserByUsername(String username);
}
