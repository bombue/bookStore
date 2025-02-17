package ru.akiselev.bookStore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.akiselev.bookStore.dto.BookDTO;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.payload.exceptions.AuthorNotFoundException;
import ru.akiselev.bookStore.repositories.AuthorsRepository;

@Mapper
public abstract class BookMapper {
    public abstract Book toBook(BookDTO bookDTO);
    @Mapping(source = "book.author.id", target = "authorId")
    public abstract BookDTO toDto(Book book);
}