package ru.akiselev.payload.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long bookId) {
        super(String.format("Book with id %s not found", bookId));
    }
}
