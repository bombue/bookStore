package ru.akiselev.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.akiselev.enums.Cover;

@Data
@NoArgsConstructor
public class BookFilter {
    private String name;
    private String brand;
    private Cover cover;
    private String authorFirstName;
    private String authorLastName;
    private Integer count;
}
