package com.bright.srs.dto.request;

public record UserRequestDto(
        String firstName,
        String lastName,
        String username,
        String password
) {
}
