package ru.akiselev.wsbook.exceptions;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(Long authorId) {
        super(String.format("Author %s not found", authorId));
    }
}
