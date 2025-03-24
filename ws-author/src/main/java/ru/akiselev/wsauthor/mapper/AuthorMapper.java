package ru.akiselev.wsauthor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.akiselev.wsauthor.dto.AuthorDTO;
import ru.akiselev.wsauthor.model.Author;

@Mapper
public abstract class AuthorMapper {
    @Mapping(target = "firstName", expression = "java(authorDTO.createFirstName())")
    @Mapping(target = "lastName", expression = "java(authorDTO.createLastName())")
    public abstract Author toAuthor(AuthorDTO authorDTO);

    @Mapping(target = "authorName", expression = "java(author.getFirstName() + \" \" + author.getLastName())")
    public abstract AuthorDTO toDto(Author author);

}