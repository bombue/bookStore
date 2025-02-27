package ru.akiselev.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.MockMvc;
import ru.akiselev.dto.BookDTO;
import ru.akiselev.enums.Cover;
import ru.akiselev.models.BookFilter;
import ru.akiselev.services.BooksService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BooksService booksService;

    @InjectMocks
    private BookController bookController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(bookController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void readBook_ShouldReturnBook() throws Exception {
        // setup
        BookDTO bookDTO = new BookDTO(1L, "Book Title", "Book brand", Cover.HARD, 1, 1L);
        when(booksService.read(1L)).thenReturn(bookDTO);

        // go
        ResultActions result = mockMvc.perform(get("/books/1"));

        // assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("Book Title"))
                .andExpect(jsonPath("brand").value("Book brand"))
                .andExpect(jsonPath("cover").value(Cover.HARD.toString()))
                .andExpect(jsonPath("count").value(1))
                .andExpect(jsonPath("authorId").value(1));
    }

    @Test
    void createBook_ShouldReturnCreatedBook() throws Exception {
        // setup
        BookDTO bookDTO = new BookDTO(1L, "Book Title", "Book brand", Cover.HARD, 1, 1L);
        when(booksService.create(bookDTO)).thenReturn(bookDTO);

        // go
        ResultActions result = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)));

        // assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("Book Title"))
                .andExpect(jsonPath("brand").value("Book brand"))
                .andExpect(jsonPath("cover").value(Cover.HARD.toString()))
                .andExpect(jsonPath("count").value(1))
                .andExpect(jsonPath("authorId").value(1));
    }

    @Test
    void patchBook_ShouldReturnOk() throws Exception {
        // setup
        BookDTO bookDTO = new BookDTO(1L, "Book Title", "Book brand", Cover.HARD, 1, 1L);
        doNothing().when(booksService).update(bookDTO);

        // go
        ResultActions result = mockMvc.perform(patch("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)));

        // assert
        result.andExpect(status().isOk());
    }

    @Test
    void deleteBook_ShouldReturnOkStatus() throws Exception {
        // Arrange
        doNothing().when(booksService).delete(1L);

        // Act
        ResultActions result = mockMvc.perform(delete("/books/1"));

        // Assert
        result.andExpect(status().isOk());
    }

    @Test
    void findByFilter_ShouldReturnFilteredBooks() throws Exception {
        // Arrange
        BookFilter filter = new BookFilter();
        filter.setName("Book Title");
        filter.setBrand("Book brand");
        filter.setCover(Cover.HARD);
        filter.setCount(1);
        filter.setAuthorLastName("lastName");
        filter.setAuthorFirstName("firstName");


        List<BookDTO> books = Collections.singletonList(new BookDTO(1L, "Book Title", "Book brand", Cover.HARD, 1, 1L));
        when(booksService.findByFilter(filter)).thenReturn(books);

        // Act
        ResultActions result = mockMvc.perform(post("/books/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filter)));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Book Title"))
                .andExpect(jsonPath("$[0].brand").value("Book brand"))
                .andExpect(jsonPath("$[0].cover").value(Cover.HARD.toString()))
                .andExpect(jsonPath("$[0].count").value(1))
                .andExpect(jsonPath("$[0].authorId").value(1));
    }
}
