package ru.akiselev.payload.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String user) {
        super(String.format("User %s already exists", user));
    }
}
