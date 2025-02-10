package ru.akiselev.bookStore.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.bookStore.dto.BookDTO;
import ru.akiselev.bookStore.enums.Cover;
import ru.akiselev.bookStore.models.BookFilter;
import ru.akiselev.bookStore.services.BooksService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody BookDTO bookDTO) {
        booksService.create(bookDTO);
        return ResponseEntity.ok(HttpStatus.OK);
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

    @GetMapping("/filter")
    private List<BookDTO> findByFilter(@RequestParam(required = false, name = "name") String name,
                                       @RequestParam(required = false, name = "brand") String brand,
                                       @RequestParam(required = false, name = "cover") Cover cover,
                                       @RequestParam(required = false, name = "authorFirstName") String authorFirstName,
                                       @RequestParam(required = false, name = "authorLastName") String authorLastName,
                                       @RequestParam(required = false, name = "count") Integer count
                                       ) {
        BookFilter filter = new BookFilter(name, brand, cover, authorFirstName, authorLastName, count);
        return booksService.findByFilter(filter);
    }

}
