package ru.akiselev.bookStore.dto;

import lombok.*;
import ru.akiselev.bookStore.enums.Cover;

@Builder
public record BookDTO(Long id, String name, String brand, Cover cover, Integer count, Long authorId) {
}
