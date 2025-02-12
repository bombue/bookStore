package ru.akiselev.bookStore.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.bookStore.dto.BookDTO;
import ru.akiselev.bookStore.enums.Cover;
import ru.akiselev.bookStore.models.BookFilter;
import ru.akiselev.bookStore.services.BooksService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(booksService.create(bookDTO));
    }

    @GetMapping("/{id}")
    private BookDTO read(@PathVariable("id") Long id) {
        return booksService.read(id);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<HttpStatus> update(@PathVariable("id") Long id, @RequestBody BookDTO bookDTO) {
        booksService.update(id, bookDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        booksService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> findByFilter(@RequestBody BookFilter filter) {
        return ResponseEntity.ok(booksService.findByFilter(filter));
    }

}
