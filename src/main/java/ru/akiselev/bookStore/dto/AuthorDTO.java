package ru.akiselev.bookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

