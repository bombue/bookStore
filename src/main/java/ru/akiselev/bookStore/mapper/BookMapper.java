package ru.akiselev.bookStore.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.akiselev.bookStore.dto.BookDTO;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.payload.exceptions.AuthorNotFoundException;
import ru.akiselev.bookStore.repositories.AuthorsRepository;
import ru.akiselev.bookStore.services.AuthorsService;

@Mapper
public abstract class BookMapper {
    @Autowired
    protected AuthorsRepository authorsRepository;

    public Book toBook(BookDTO bookDTO) {
        return new Book(bookDTO.id(),
                bookDTO.name(),
                bookDTO.brand(),
                bookDTO.cover(),
                bookDTO.count(),
                authorsRepository.findById(bookDTO.authorId()).orElseThrow(() -> new AuthorNotFoundException(bookDTO.authorId()))
        );
    }

    public BookDTO toDto(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .brand(book.getBrand())
                .cover(book.getCover())
                .count(book.getCount())
                .authorId(book.getAuthor()==null ? null : book.getAuthor().getId())
                .build();
    }
}

//@Mapper
//public interface BookMapper {
//    @Mapping(source = "authorId", target = "author", qualifiedByName = "authorIdToAuthor")
//    Book toBook(BookDTO bookDTO);
//    @Mapping(source = "author.id", target = "author_id")
//    BookDTO toDto(Book book);
//    @Named("authorIdToAuthor")
//    static Author authorIdToAuthor(Long authorId) {
//        Author author = new Author();
//        author.setId(authorId);
//        return author;
//    }
//    @Named("authorToAuthorId")
//    static Long authorToAuthorId(Author author) {
//        if (author != null) {
//            return author.getId();
//        }
//        else {
//            return null;
//        }
//    }
//}
