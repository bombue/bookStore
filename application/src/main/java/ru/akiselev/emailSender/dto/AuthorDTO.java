package ru.akiselev.emailSender.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AuthorDTO(Long id, String authorName, List<Long> bookIds) {
    public String createFirstName() {
        return List.of(authorName.split(" ")).get(0);
    }

    public String createLastName() {
        return List.of(authorName.split(" ")).get(1);
    }
}

