package ru.akiselev.wsbook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.akiselev.wsbook.model.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
