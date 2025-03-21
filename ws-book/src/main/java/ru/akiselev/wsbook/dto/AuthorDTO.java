package ru.akiselev.wsbook.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record AuthorDTO(Long id, @NotEmpty String authorName) {
}

