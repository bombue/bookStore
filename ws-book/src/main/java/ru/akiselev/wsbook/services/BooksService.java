package ru.akiselev.wsbook.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
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
import ru.akiselev.wsbook.model.AuthorServiceClient;
import ru.akiselev.wsbook.model.Book;
import ru.akiselev.wsbook.model.BookFilter;
import ru.akiselev.wsbook.repositories.BookSpecification;
import ru.akiselev.wsbook.repositories.BooksRepository;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final BookMapper bookMapper;
    private final BookSpecification bookSpecification;
//    private final RestTemplate restTemplate;
//    private final Environment environment;
    private final AuthorServiceClient authorServiceClient;

    @Transactional
    public BookDTO create(BookDTO bookDTO) {
        Book book = bookMapper.toBook(bookDTO);
//        ResponseEntity<AuthorDTO> authorResponse = restTemplate.getForEntity(String.format("%s/authors/%d", environment.getProperty("author.url"), book.getAuthor_id()), AuthorDTO.class);
//        if (!authorResponse.getStatusCode().equals(HttpStatus.OK)) {
//            throw new AuthorNotFoundException(book.getAuthor_id());
//        }
        authorServiceClient.getAuthor(book.getAuthor_id());

//        ResponseEntity<AuthorDTO> authorResponse = restTemplate.exchange("", HttpMethod.GET, null, new ParameterizedTypeReference<AuthorDTO>() {
//        });
//        book.setAuthor(authorsRepository.findById(bookDTO.authorId()).orElseThrow(() -> new AuthorNotFoundException(bookDTO.authorId())));
        booksRepository.save(book);
        return bookMapper.toDto(book);
    }

    public BookDTO read(Long id) {
        Book book = booksRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
//        ResponseEntity<AuthorDTO> authorResponse = restTemplate.getForEntity(String.format("%s/authors/%d", environment.getProperty("author.url"), book.getAuthor_id()), AuthorDTO.class);
//        if (!authorResponse.getStatusCode().equals(HttpStatus.OK)) {
//            throw new AuthorNotFoundException(book.getAuthor_id());
//        }
        log.info("akiselev some message before");
        AuthorDTO authorDTO = authorServiceClient.getAuthor(book.getAuthor_id());
        log.info("akiselev some message after");
        book.setAuthor_id(authorDTO.id());
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
