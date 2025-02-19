package ru.akiselev.bookStore.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.bookStore.dto.BookDTO;
import ru.akiselev.bookStore.enums.Cover;
import ru.akiselev.bookStore.mapper.BookMapper;
import ru.akiselev.bookStore.models.Author;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.models.BookFilter;
import ru.akiselev.bookStore.payload.exceptions.BookNotFoundException;
import ru.akiselev.bookStore.repositories.AuthorsRepository;
import ru.akiselev.bookStore.repositories.BooksRepository;
import ru.akiselev.bookStore.repositories.specifications.BookSpecification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BooksServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @Mock
    private AuthorsRepository authorsRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookSpecification bookSpecification;

    @InjectMocks
    private BooksService booksService;

    private BookDTO bookDTO;
    private Book book;
    private Author author;

    @BeforeEach
    void setUp() {
        // Инициализация тестовых данных
        author = new Author(1L, "John", "Doe", Collections.emptyList());
        book = new Book(1L, "Book Title", "Brand", Cover.HARD, 10, author);
        bookDTO = new BookDTO(1L, "Book Title", "Brand", Cover.HARD, 10, 1L);
    }

    @Test
    @Transactional
    void create_ShouldReturnCreatedBookDTO() {
        // Arrange
        when(bookMapper.toBook(bookDTO)).thenReturn(book);
        when(authorsRepository.findById(1L)).thenReturn(Optional.of(author));
        when(booksRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);

        // Act
        BookDTO result = booksService.create(bookDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Book Title", result.name());
        assertEquals("Brand", result.brand());
        assertEquals(Cover.HARD, result.cover());
        assertEquals(10, result.count());
        assertEquals(1L, result.authorId());

        verify(bookMapper).toBook(bookDTO);
        verify(authorsRepository).findById(1L);
        verify(booksRepository).save(book);
        verify(bookMapper).toDto(book);
    }

    @Test
    void read_ShouldReturnBookDTO() {
        // Arrange
        when(booksRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDTO);

        // Act
        BookDTO result = booksService.read(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Book Title", result.name());
        assertEquals("Brand", result.brand());
        assertEquals(Cover.HARD, result.cover());
        assertEquals(10, result.count());
        assertEquals(1L, result.authorId());

        verify(booksRepository).findById(1L);
        verify(bookMapper).toDto(book);
    }

    @Test
    void read_ShouldThrowBookNotFoundException() {
        // Arrange
        when(booksRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookNotFoundException.class, () -> booksService.read(1L));

        verify(booksRepository).findById(1L);
    }

    @Test
    @Transactional
    void update_ShouldSaveBook() {
        // Arrange
        when(bookMapper.toBook(bookDTO)).thenReturn(book);

        // Act
        booksService.update(bookDTO);

        // Assert
        verify(bookMapper).toBook(bookDTO);
        verify(booksRepository).save(book);
    }

    @Test
    @Transactional
    void delete_ShouldDeleteBook() {
        // Act
        booksService.delete(1L);

        // Assert
        verify(booksRepository).deleteById(1L);
    }

    @Test
    void findByFilter_ShouldReturnFilteredBooks() {
        // Arrange
        BookFilter filter = new BookFilter();
        filter.setName("Book Title");
        filter.setBrand("Book brand");
        filter.setCover(Cover.HARD);
        filter.setCount(1);
        filter.setAuthorLastName("lastName");
        filter.setAuthorFirstName("firstName");
        when(booksRepository.findAll(bookSpecification.byFilter(filter))).thenReturn(Collections.singletonList(book));
        when(bookMapper.toDto(book)).thenReturn(bookDTO);

        // Act
        List<BookDTO> result = booksService.findByFilter(filter);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookDTO, result.get(0));

        verify(booksRepository).findAll(bookSpecification.byFilter(filter));
        verify(bookMapper).toDto(book);
    }
}