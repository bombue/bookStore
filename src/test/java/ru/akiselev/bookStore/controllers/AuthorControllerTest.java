package ru.akiselev.bookStore.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.akiselev.bookStore.dto.AuthorDTO;
import ru.akiselev.bookStore.services.AuthorsService;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    @Mock
    private AuthorsService authorsService;

    @InjectMocks
    private AuthorController authorController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(authorController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createAuthor_ShouldReturnCreatedAuthorDTO() throws Exception {
        // Arrange
        AuthorDTO authorDTO = new AuthorDTO(1L, "John Doe", Collections.emptyList());
        when(authorsService.create(authorDTO)).thenReturn(authorDTO);

        // Act
        ResultActions result = mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorDTO)));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.authorName").value("John Doe"))
                .andExpect(jsonPath("$.bookIds").isEmpty());

        verify(authorsService).create(authorDTO);
    }

    @Test
    void readAuthor_ShouldReturnAuthorDTO() throws Exception {
        // Arrange
        AuthorDTO authorDTO = new AuthorDTO(1L, "John Doe", Collections.emptyList());
        when(authorsService.read(1L)).thenReturn(authorDTO);

        // Act
        ResultActions result = mockMvc.perform(get("/authors/1"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.authorName").value("John Doe"))
                .andExpect(jsonPath("$.bookIds").isEmpty());

        verify(authorsService).read(1L);
    }

    @Test
    void deleteAuthor_ShouldReturnOkStatus() throws Exception {
        // Arrange
        doNothing().when(authorsService).delete(1L);

        // Act
        ResultActions result = mockMvc.perform(delete("/authors/1"));

        // Assert
        result.andExpect(status().isOk());

        verify(authorsService).delete(1L);
    }
}