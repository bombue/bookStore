package ru.akiselev.bookStore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.bookStore.dto.BookDTO;
import ru.akiselev.bookStore.mapper.BookMapper;
import ru.akiselev.bookStore.services.BooksService;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BooksService booksService, BookMapper bookMapper) {
        this.booksService = booksService;
        this.bookMapper = bookMapper;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody BookDTO bookDTO) {
        booksService.create(bookMapper.toBook(bookDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private BookDTO read(@PathVariable("id") Long id) {
        return bookMapper.toDto(booksService.read(id));
    }

    @PatchMapping("/{id}")
    private ResponseEntity<HttpStatus> update(@PathVariable("id") Long id, @RequestBody BookDTO bookDTO) {
        booksService.update(id, bookMapper.toBook(bookDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        booksService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
