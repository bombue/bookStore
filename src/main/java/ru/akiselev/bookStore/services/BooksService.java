package ru.akiselev.bookStore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.models.BookFilter;
import ru.akiselev.bookStore.repositories.BooksRepository;
import ru.akiselev.bookStore.repositories.CustomBookRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final CustomBookRepository customBookRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, CustomBookRepository customBookRepository) {
        this.booksRepository = booksRepository;
        this.customBookRepository = customBookRepository;
    }

    @Transactional
    public void create(Book book) {
        this.booksRepository.save(book);
    }

    public Book read(Long id) {
        return this.booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(Long id, Book book) {
        book.setId(id);
        this.booksRepository.save(book);
    }

    @Transactional
    public void delete(Long id) {
        this.booksRepository.deleteById(id);
    }

    public List<Book> findByFilter(BookFilter filter) {
        return this.customBookRepository.findByFilter(filter);
    }
}
