package ru.akiselev.bookStore.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.bookStore.dto.AuthorDTO;
import ru.akiselev.bookStore.enums.Cover;
import ru.akiselev.bookStore.mapper.AuthorMapper;
import ru.akiselev.bookStore.models.Author;
import ru.akiselev.bookStore.models.Book;
import ru.akiselev.bookStore.payload.exceptions.AuthorNotFoundException;
import ru.akiselev.bookStore.repositories.AuthorsRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorsServiceTest {
    @Mock
    private AuthorsRepository authorsRepository;
    @Mock
    private AuthorMapper authorMapper;
    @InjectMocks
    private AuthorsService authorsService;
    private AuthorDTO authorDTO;
    private Author author;

    @BeforeEach
    void setUp() {
        // Инициализация тестовых данных
        author = new Author(1L, "John", "Doe", Collections.emptyList());
        authorDTO = new AuthorDTO(1L, "John Doe", Collections.emptyList());
    }

    @Test
    @Transactional
    void createAuthor() {
        when(authorMapper.toAuthor(authorDTO)).thenReturn(author);
        when(authorsRepository.save(author)).thenReturn(author);
        when(authorMapper.toDto(author)).thenReturn(authorDTO);

        // go
        AuthorDTO result = authorsService.create(authorDTO);

        // assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("John Doe", result.authorName());
        assertTrue(result.bookIds().isEmpty());

        verify(authorMapper).toAuthor(authorDTO);
        verify(authorsRepository).save(author);
        verify(authorMapper).toDto(author);
    }

    @Test
    void read_ShouldReturnAuthorDTO() {
        // Arrange
        when(authorsRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(authorDTO);

        // Act
        AuthorDTO result = authorsService.read(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("John Doe", result.authorName());
        assertTrue(result.bookIds().isEmpty());

        verify(authorsRepository).findById(1L);
        verify(authorMapper).toDto(author);
    }

    @Test
    void read_ShouldThrowAuthorNotFoundException() {
        // Arrange
        when(authorsRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AuthorNotFoundException.class, () -> authorsService.read(1L));

        verify(authorsRepository).findById(1L);
    }

    @Test
    @Transactional
    void delete_ShouldDeleteAuthor() {
        // Arrange
        doNothing().when(authorsRepository).deleteById(1L);

        // Act
        authorsService.delete(1L);

        // Assert
        verify(authorsRepository).deleteById(1L);
    }
}
