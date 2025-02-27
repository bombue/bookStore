package ru.akiselev.dto;

import lombok.Builder;

@Builder
public record RegisteredUserDTO(String username, String email, String generatedUrl) {
}
