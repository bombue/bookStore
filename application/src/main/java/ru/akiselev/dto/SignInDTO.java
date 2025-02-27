package ru.akiselev.dto;

import lombok.Builder;

@Builder
public record SignInDTO(String username, String password) {
}
