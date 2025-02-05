package ru.akiselev.bookStore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.akiselev.bookStore.dto.AuthorDTO;
import ru.akiselev.bookStore.models.Author;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Mapper
public interface AuthorMapper {

    @Mapping(target = "bookList", ignore = true)
    @Mapping(source = "authorName", target = "first_name", qualifiedByName = "authorNameToFirstName")
    @Mapping(source = "authorName", target = "last_name", qualifiedByName = "authorNameToLastName")
    Author toAuthor(AuthorDTO dto);

    @Mapping(target = "bookIds", ignore = true)
    @Mapping(target = "authorName", expression = "java(author.getFirst_name() + \" \" + author.getLast_name())")
    AuthorDTO toDto(Author author);

    @Named("authorNameToFirstName")
    static String authorNameToFirstName(String authorName) {
        return List.of(authorName.split(" ")).get(0);
    }

    @Named("authorNameToLastName")
    static String authorNameToLastName(String authorName) {
        return List.of(authorName.split(" ")).get(1);
    }

}
