package ru.akiselev.bookStore.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthErrorResponse {
    private String message;
    private long timestamp;
}
