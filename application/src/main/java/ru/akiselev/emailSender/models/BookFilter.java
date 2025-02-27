package ru.akiselev.emailSender.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.akiselev.emailSender.enums.Cover;

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
