package ru.akiselev.bookStore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.repositories.BooksRepository;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
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
}
