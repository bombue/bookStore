package ru.akiselev.bookStore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.bookStore.dto.AuthorDTO;
import ru.akiselev.bookStore.mapper.AuthorMapper;
import ru.akiselev.bookStore.services.AuthorsService;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorsService authorsService;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorController(AuthorsService authorsService, AuthorMapper authorMapper) {
        this.authorsService = authorsService;
        this.authorMapper = authorMapper;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody AuthorDTO authorDTO) {
        authorsService.create(authorMapper.toAuthor(authorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public AuthorDTO read(@PathVariable("id") Long id) {
        return authorMapper.toDto(authorsService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        authorsService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
