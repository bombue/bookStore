package ru.akiselev.bookStore.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.akiselev.bookStore.dto.AuthorDTO;
import ru.akiselev.bookStore.models.Author;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.payload.exceptions.BookNotFoundException;
import ru.akiselev.bookStore.repositories.BooksRepository;
import ru.akiselev.bookStore.services.BooksService;

import java.util.List;
import java.util.Optional;

@Mapper
public abstract class AuthorMapper {
    @Autowired
    protected BooksRepository booksRepository;

    public Author toAuthor(AuthorDTO authorDTO) {
        return new Author(authorDTO.id(),
                List.of(authorDTO.authorName().split(" ")).get(0),
                List.of(authorDTO.authorName().split(" ")).get(1),
                authorDTO.bookIds().stream().map(id -> booksRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id))).toList()
        );
    }

    public AuthorDTO toDto(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .authorName(String.format("%s %s", author.getFirst_name(), author.getLast_name()))
                .bookIds(author.getBookList().stream().map(Book::getId).toList())
                .build();
    }
}