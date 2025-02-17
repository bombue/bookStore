package ru.akiselev.bookStore.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.bookStore.dto.BookDTO;
import ru.akiselev.bookStore.mapper.BookMapper;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.models.BookFilter;
import ru.akiselev.bookStore.payload.exceptions.AuthorNotFoundException;
import ru.akiselev.bookStore.payload.exceptions.BookNotFoundException;
import ru.akiselev.bookStore.repositories.AuthorsRepository;
import ru.akiselev.bookStore.repositories.BooksRepository;
import ru.akiselev.bookStore.repositories.specifications.BookSpecification;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final AuthorsRepository authorsRepository;
    private final BookMapper bookMapper;
    private final BookSpecification bookSpecification;

    @Transactional
    public BookDTO create(BookDTO bookDTO) {
        Book book = bookMapper.toBook(bookDTO);
        book.setAuthor(authorsRepository.findById(bookDTO.authorId()).orElseThrow(() -> new AuthorNotFoundException(bookDTO.authorId())));
        booksRepository.save(book);
        return bookMapper.toDto(book);
    }

    public BookDTO read(Long id) {
        return booksRepository.findById(id).map(bookMapper::toDto).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional
    public void update(BookDTO bookDTO) {
        Book book = bookMapper.toBook(bookDTO);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(Long id) {
        booksRepository.deleteById(id);
    }

    public List<BookDTO> findByFilter(BookFilter filter) {
//        Specification<Book> spec = bookSpecification.byFilter(filter);
        return booksRepository.findAll(bookSpecification.byFilter(filter))
                .stream().map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}
