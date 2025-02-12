package ru.akiselev.bookStore.dto;

import lombok.Builder;

@Builder
public record RegisteredUserDTO(String username, String email, String generatedUrl) {
}
