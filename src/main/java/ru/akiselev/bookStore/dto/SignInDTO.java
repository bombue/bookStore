package ru.akiselev.bookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class SignInDTO {
//    private String username;
//    private String password;
//}
@Builder
public record SignInDTO(String username, String password) {
}
