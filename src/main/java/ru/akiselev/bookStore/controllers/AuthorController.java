package ru.akiselev.bookStore.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.bookStore.dto.AuthorDTO;
import ru.akiselev.bookStore.payload.exceptions.AuthorNotFoundException;
import ru.akiselev.bookStore.payload.response.ErrorResponse;
import ru.akiselev.bookStore.services.AuthorsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorsService authorsService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AuthorDTO authorDTO) {
        return ResponseEntity.ok(authorsService.create(authorDTO));
    }

    @GetMapping("/{id}")
    public AuthorDTO read(@PathVariable("id") Long id) {
        return authorsService.read(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        authorsService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
