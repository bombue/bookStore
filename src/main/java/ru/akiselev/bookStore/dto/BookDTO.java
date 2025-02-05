package ru.akiselev.bookStore.dto;

import lombok.*;
import ru.akiselev.bookStore.enums.Cover;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String name;
    private String brand;
    private Cover cover;  // Enum типа обложки: твердый или мягкий переплет
    private Integer count;
    private Long author_id;
}
