package ru.akiselev.wsbook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.akiselev.wsbook.dto.BookDTO;
import ru.akiselev.wsbook.model.Book;

@Mapper
public abstract class BookMapper {
    @Mapping(source = "authorId", target = "author_id")
    public abstract Book toBook(BookDTO bookDTO);
    @Mapping(source = "author_id", target = "authorId")
    public abstract BookDTO toDto(Book book);
}