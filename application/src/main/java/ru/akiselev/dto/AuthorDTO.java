package ru.akiselev.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record AuthorDTO(@NotEmpty Long id, @NotEmpty String authorName, List<Long> bookIds) {
    public String createFirstName() {
        return List.of(authorName.split(" ")).get(0);
    }

    public String createLastName() {
        return List.of(authorName.split(" ")).get(1);
    }
}

