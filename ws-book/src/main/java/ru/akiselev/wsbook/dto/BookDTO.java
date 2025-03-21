package ru.akiselev.wsbook.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.akiselev.wsbook.enums.Cover;

@Builder
public record BookDTO(Long id, @NotNull String name, @NotNull String brand, @NotNull Cover cover, @NotNull Integer count, @NotNull Long authorId) {
}
