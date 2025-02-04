package ru.akiselev.bookStore.mapper;

import org.mapstruct.Mapper;
import ru.akiselev.bookStore.dto.BookDTO;
import ru.akiselev.bookStore.models.Book;

@Mapper
public interface BookMapper {
    Book toBook(BookDTO bookDTO);
    BookDTO toDto(Book book);
}
