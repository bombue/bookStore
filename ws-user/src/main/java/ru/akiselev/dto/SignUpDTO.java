package ru.akiselev.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SignUpDTO(@NotNull(message = "username can not be null") @NotEmpty String username, @NotEmpty  String password, @NotEmpty @Email String email) {
}