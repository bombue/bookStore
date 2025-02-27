package ru.akiselev.emailSender.dto;

import lombok.Builder;

@Builder
public record RegisteredUserDTO(String username, String email, String generatedUrl) {
}
