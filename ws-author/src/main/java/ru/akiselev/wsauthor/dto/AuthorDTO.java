package ru.akiselev.wsauthor.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record AuthorDTO(Long id, @NotEmpty String authorName) {
    public String createFirstName() {
        return List.of(authorName.split(" ")).get(0);
    }

    public String createLastName() {
        return List.of(authorName.split(" ")).get(1);
    }
}

