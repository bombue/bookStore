package ru.akiselev.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record RegisteredUserDTO(@NotEmpty String username, @NotEmpty @Email String email, @NotEmpty String generatedUrl) {
}
