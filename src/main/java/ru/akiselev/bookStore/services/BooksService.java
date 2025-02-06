package ru.akiselev.bookStore.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.models.BookFilter;
import ru.akiselev.bookStore.repositories.BooksRepository;
//import ru.akiselev.bookStore.repositories.CustomBookRepositoryImpl;

import java.util.List;

import static ru.akiselev.bookStore.repositories.BooksRepository.Specs.*;

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

    public List<Book> findByFilter(BookFilter filter) {
        return this.booksRepository.findAll(byFilter(filter));
//        return this.booksRepository.findAll(byName(filter.getName())
//                .and(byBrand(filter.getBrand()))
//                .and(byCover(filter.getCover()))
//                .and(byCount(filter.getCount())));
    }
}
