package ru.akiselev.bookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class SignUpDTO {
//    private String username;
//    private String password;
//    private String email;
//}

@Builder
public record SignUpDTO(String username, String password, String email) {
}