package ru.akiselev.bookStore.payload.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException (String user) {
        super(String.format("User %s not found", user));
    }
}
