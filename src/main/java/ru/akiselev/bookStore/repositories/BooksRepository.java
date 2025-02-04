package ru.akiselev.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akiselev.bookStore.models.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
}
