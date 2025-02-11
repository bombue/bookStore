package ru.akiselev.bookStore.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.bookStore.dto.BookDTO;
import ru.akiselev.bookStore.mapper.BookMapper;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.models.BookFilter;
import ru.akiselev.bookStore.payload.exceptions.BookNotFoundException;
import ru.akiselev.bookStore.repositories.BooksRepository;
//import ru.akiselev.bookStore.repositories.CustomBookRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

import static ru.akiselev.bookStore.repositories.BooksRepository.Specs.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final BookMapper bookMapper;

    @Transactional
    public BookDTO create(BookDTO bookDTO) {
        return bookMapper.toDto(booksRepository.save(bookMapper.toBook(bookDTO)));
    }

    public BookDTO readDto(Long id) {
        return booksRepository.findById(id).map(bookMapper::toDto).orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book read(Long id) {
        return booksRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional
    public void update(Long id, BookDTO bookDTO) {
        Book book = bookMapper.toBook(bookDTO);
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(Long id) {
        booksRepository.deleteById(id);
    }

    public List<BookDTO> findByFilter(BookFilter filter) {
        return booksRepository.findAll(byFilter(filter)).stream().map(bookMapper::toDto).collect(Collectors.toList());
    }
}
