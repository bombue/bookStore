package ru.akiselev.emailSender.dto;

import lombok.Builder;

@Builder
public record SignInDTO(String username, String password) {
}
