package ru.akiselev.wsbook.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record AuthorDTO(Long id, @NotEmpty String authorName) {
}

