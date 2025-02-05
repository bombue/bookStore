package ru.akiselev.bookStore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.akiselev.bookStore.enums.Cover;

@Data
@AllArgsConstructor
public class BookFilter {
    private String name;
    private String brand;
    private Cover cover;
    private String authorFirstName;
    private String authorLastName;
    private Integer count;
}
