package ru.akiselev.emailSender.dto;

import lombok.*;
import ru.akiselev.emailSender.enums.Cover;

@Builder
public record BookDTO(Long id, String name, String brand, Cover cover, Integer count, Long authorId) {
}
