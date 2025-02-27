package ru.akiselev.emailSender.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.akiselev.emailSender.dto.AuthorDTO;
import ru.akiselev.emailSender.models.Author;
import ru.akiselev.emailSender.models.Book;

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