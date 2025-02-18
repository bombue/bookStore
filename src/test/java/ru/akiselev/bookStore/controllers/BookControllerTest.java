package ru.akiselev.bookStore.controllers;

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
import ru.akiselev.bookStore.dto.BookDTO;
import ru.akiselev.bookStore.enums.Cover;
import ru.akiselev.bookStore.security.jwt.AuthTokenFilter;
import ru.akiselev.bookStore.services.BooksService;

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
        // Arrange
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

}
