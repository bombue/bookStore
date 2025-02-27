package ru.akiselev.dto;

import lombok.*;
import ru.akiselev.enums.Cover;

@Builder
public record BookDTO(Long id, String name, String brand, Cover cover, Integer count, Long authorId) {
}
