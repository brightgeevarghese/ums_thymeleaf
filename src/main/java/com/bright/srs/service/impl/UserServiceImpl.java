package com.bright.srs.service.impl;

import com.bright.srs.dto.request.UserRequestDto;
import com.bright.srs.dto.response.UserResponseDto;
import com.bright.srs.model.User;
import com.bright.srs.repository.UserRepository;
import com.bright.srs.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<UserResponseDto> createUser(UserRequestDto userRequestDto) {
        if (userRepository.findByUsername(userRequestDto.username()).isPresent()) {
            return Optional.empty();
        }
        User user = new User(userRequestDto.firstName(), userRequestDto.lastName(), userRequestDto.username(), userRequestDto.password());
        User savedUser = userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto(savedUser.getUsername());
        return Optional.of(userResponseDto);
    }

    @Override
    public Optional<UserResponseDto> updateUser(String username, UserRequestDto userRequestDto) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(userRequestDto.password());
            user.setFirstName(userRequestDto.firstName());
            user.setLastName(userRequestDto.lastName());
            User savedUser = userRepository.save(user);
            UserResponseDto userResponseDto = new UserResponseDto(savedUser.getUsername());
            return Optional.of(userResponseDto);
        }
        return Optional.empty();
    }

    @Override
    public void deleteUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
        } else {
            log.atWarn().log("User not found: {} for deletion.", username);
        }
    }

    @Override
    public List<UserResponseDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : users) {
            userResponseDtos.add(new UserResponseDto(user.getUsername()));
        }
        return userResponseDtos;
    }

    @Override
    public Optional<UserResponseDto> findUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return Optional.of(new UserResponseDto(user.getUsername()));
        }
        return Optional.empty();
    }
}
