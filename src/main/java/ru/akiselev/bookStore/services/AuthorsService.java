package ru.akiselev.bookStore.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.bookStore.dto.AuthorDTO;
import ru.akiselev.bookStore.mapper.AuthorMapper;
import ru.akiselev.bookStore.payload.exceptions.AuthorNotFoundException;
import ru.akiselev.bookStore.repositories.AuthorsRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthorsService {

    private final AuthorsRepository authorsRepository;
    private final AuthorMapper authorMapper;

    @Transactional
    public void create(AuthorDTO authorDTO) {
        authorsRepository.save(authorMapper.toAuthor(authorDTO));
    }

    public AuthorDTO read(Long id) {
        return authorsRepository.findById(id).map(authorMapper::toDto).orElseThrow(AuthorNotFoundException::new);
    }

    @Transactional
    public void delete(Long id) {
        authorsRepository.deleteById(id);
    }

}
