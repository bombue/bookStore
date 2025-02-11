package ru.akiselev.bookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Builder
public record AuthorDTO(Long id, String authorName, List<Long> bookIds) {
}
//public class AuthorDTO {
//    private Long id;
//    private String authorName;
//    private List<Long> bookIds = new ArrayList<>();
//}
