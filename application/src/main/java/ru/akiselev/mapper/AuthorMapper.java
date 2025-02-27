package ru.akiselev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.akiselev.dto.AuthorDTO;
import ru.akiselev.models.Author;
import ru.akiselev.models.Book;

import java.util.List;

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