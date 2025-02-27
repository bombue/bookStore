package ru.akiselev.dto;

import lombok.Builder;

@Builder
public record SignUpDTO(String username, String password, String email) {
}