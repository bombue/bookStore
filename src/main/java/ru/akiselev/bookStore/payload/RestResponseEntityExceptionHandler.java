package ru.akiselev.bookStore.payload;

import org.hibernate.WrongClassException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.akiselev.bookStore.payload.exceptions.*;
import ru.akiselev.bookStore.payload.response.ErrorResponse;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {AuthorNotFoundException.class, UserNotFoundException.class})
    private ResponseEntity<ErrorResponse> handleExceptionNotFound(RuntimeException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class, EmailAlreadyExistsException.class})
    private ResponseEntity<ErrorResponse> handleExceptionAlreadyExists(RuntimeException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {WrongUrlAuthException.class})
    private ResponseEntity<ErrorResponse> handleExceptionWithAuthUrl(RuntimeException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
