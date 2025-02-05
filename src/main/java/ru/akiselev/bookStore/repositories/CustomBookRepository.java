package ru.akiselev.bookStore.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.models.BookFilter;

import java.util.List;

@Component
public class CustomBookRepository {

    @PersistenceContext
    private EntityManager em;
    public List<Book> findByFilter(BookFilter filter) {

    }
}
