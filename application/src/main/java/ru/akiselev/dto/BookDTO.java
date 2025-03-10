package ru.akiselev.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.akiselev.enums.Cover;

@Builder
public record BookDTO(@NotNull Long id, @NotNull String name, @NotNull String brand, @NotNull Cover cover, @NotNull Integer count, @NotNull Long authorId) {
}
