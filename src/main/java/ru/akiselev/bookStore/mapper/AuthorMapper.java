package ru.akiselev.bookStore.mapper;

import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
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
    @Mapping(target = "firstName", expression = "java(authorDTO.createFirstName())")
    @Mapping(target = "lastName", expression = "java(authorDTO.createLastName())")
    public abstract Author toAuthor(AuthorDTO authorDTO);

    @Mapping(target = "bookIds", source = "bookList", qualifiedByName = "getBookIds")
    @Mapping(target = "authorName", expression = "java(author.getFirstName() + \" \" + author.getLastName())")
    public abstract AuthorDTO toDto(Author author);

    @Named("getBookIds")
    public List<Long> getBookIds(List<Book> books) {
        return books.stream().map(Book::getId).toList();
    }
}