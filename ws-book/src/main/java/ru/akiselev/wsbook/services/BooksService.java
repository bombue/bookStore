package ru.akiselev.wsbook.services;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.akiselev.wsbook.dto.AuthorDTO;
import ru.akiselev.wsbook.dto.BookDTO;
import ru.akiselev.wsbook.exceptions.AuthorNotFoundException;
import ru.akiselev.wsbook.exceptions.BookNotFoundException;
import ru.akiselev.wsbook.mapper.BookMapper;
import ru.akiselev.wsbook.model.Book;
import ru.akiselev.wsbook.model.BookFilter;
import ru.akiselev.wsbook.repositories.BookSpecification;
import ru.akiselev.wsbook.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
//    private final AuthorsRepository authorsRepository;
    private final BookMapper bookMapper;
    private final BookSpecification bookSpecification;
    private final RestTemplate restTemplate;
    private final Environment environment;

    @Transactional
    public BookDTO create(BookDTO bookDTO) {
        Book book = bookMapper.toBook(bookDTO);
        ResponseEntity<AuthorDTO> authorResponse = restTemplate.getForEntity(String.format("%s/%d", environment.getProperty("author.url"), book.getAuthor_id()), AuthorDTO.class);
        if (!authorResponse.getStatusCode().equals(HttpStatus.OK)) {
            throw new AuthorNotFoundException(book.getAuthor_id());
        }

//        ResponseEntity<AuthorDTO> authorResponse = restTemplate.exchange("", HttpMethod.GET, null, new ParameterizedTypeReference<AuthorDTO>() {
//        });
//        book.setAuthor(authorsRepository.findById(bookDTO.authorId()).orElseThrow(() -> new AuthorNotFoundException(bookDTO.authorId())));
        booksRepository.save(book);
        return bookMapper.toDto(book);
    }

    public BookDTO read(Long id) {
        Book book = booksRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        ResponseEntity<AuthorDTO> authorResponse = restTemplate.getForEntity(String.format("%s/%d", environment.getProperty("author.url"), book.getAuthor_id()), AuthorDTO.class);
        if (!authorResponse.getStatusCode().equals(HttpStatus.OK)) {
            throw new AuthorNotFoundException(book.getAuthor_id());
        }
        book.setAuthor_id(authorResponse.getBody().id());
        return bookMapper.toDto(book);

//        return booksRepository.findById(id).map(bookMapper::toDto).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional
    public void update(BookDTO bookDTO) {
        Book book = bookMapper.toBook(bookDTO);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(Long id) {
        booksRepository.deleteById(id);
    }

    public List<BookDTO> findByFilter(BookFilter filter) {
        return booksRepository.findAll(bookSpecification.byFilter(filter))
                .stream().map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}
