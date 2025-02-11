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
//public interface AuthorMapper {
//
//    @Mapping(target = "bookList", ignore = true)
//    @Mapping(source = "authorName", target = "first_name", qualifiedByName = "authorNameToFirstName")
//    @Mapping(source = "authorName", target = "last_name", qualifiedByName = "authorNameToLastName")
//    Author toAuthor(AuthorDTO dto);
//
//    @Mapping(target = "bookIds", ignore = true)
//    @Mapping(target = "authorName", expression = "java(author.getFirst_name() + \" \" + author.getLast_name())")
//    AuthorDTO toDto(Author author);
//
//    @Named("authorNameToFirstName")
//    static String authorNameToFirstName(String authorName) {
//        return List.of(authorName.split(" ")).get(0);
//    }
//
//    @Named("authorNameToLastName")
//    static String authorNameToLastName(String authorName) {
//        return List.of(authorName.split(" ")).get(1);
//    }
//
//}
