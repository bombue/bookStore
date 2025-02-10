package ru.akiselev.bookStore.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.bookStore.dto.BookDTO;
import ru.akiselev.bookStore.mapper.BookMapper;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.models.BookFilter;
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
    public void create(BookDTO bookDTO) {
        booksRepository.save(bookMapper.toBook(bookDTO));
    }

    public BookDTO read(Long id) {
        return bookMapper.toDto(booksRepository.findById(id).orElse(null));
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
