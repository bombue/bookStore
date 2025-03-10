package ru.akiselev.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SignInDTO(@NotEmpty String username, @NotEmpty String password) {
}
