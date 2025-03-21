package ru.akiselev.wsauthor.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.wsauthor.dto.AuthorDTO;
import ru.akiselev.wsauthor.services.AuthorsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorsService authorsService;

    @PostMapping
    public ResponseEntity<AuthorDTO> create(@Valid @RequestBody AuthorDTO authorDTO) {
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
