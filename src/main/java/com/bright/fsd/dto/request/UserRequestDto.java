package com.bright.fsd.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
