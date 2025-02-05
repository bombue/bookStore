package ru.akiselev.bookStore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.akiselev.bookStore.dto.BookDTO;
import ru.akiselev.bookStore.models.Author;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.services.AuthorsService;

@Mapper
public interface BookMapper {
    @Mapping(source = "author_id", target = "author", qualifiedByName = "authorIdToAuthor")
    Book toBook(BookDTO bookDTO);
    @Mapping(source = "author", target = "author_id", qualifiedByName = "authorToAuthorId")
    BookDTO toDto(Book book);
    @Named("authorIdToAuthor")
    static Author authorIdToAuthor(Long authorId) {
        Author author = new Author();
        author.setId(authorId);
        return author;
    }
    @Named("authorToAuthorId")
    static Long authorToAuthorId(Author author) {
        if (author != null)
            return author.getId();
        else return null;
    }
}
