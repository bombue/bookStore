package ru.akiselev.emailSender.dto;

import lombok.Builder;

@Builder
public record SignUpDTO(String username, String password, String email) {
}