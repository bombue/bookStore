package ru.akiselev.emailSender.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.akiselev.emailSender.dto.BookDTO;
import ru.akiselev.emailSender.models.Book;

@Mapper
public abstract class BookMapper {
    public abstract Book toBook(BookDTO bookDTO);
    @Mapping(source = "book.author.id", target = "authorId")
    public abstract BookDTO toDto(Book book);
}