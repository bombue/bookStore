package ru.akiselev.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.dto.BookDTO;
import ru.akiselev.models.BookFilter;
import ru.akiselev.services.BooksService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;

    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(booksService.create(bookDTO));
    }

    @GetMapping("/{id}")
    private BookDTO read(@PathVariable("id") Long id) {
        return booksService.read(id);
    }

    @PatchMapping
    private ResponseEntity<HttpStatus> update(@RequestBody BookDTO bookDTO) {
        booksService.update(bookDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        booksService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<BookDTO>> findByFilter(@RequestBody BookFilter filter) {
        return ResponseEntity.ok(booksService.findByFilter(filter));
    }
}
