package ru.akiselev.bookStore.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.bookStore.dto.AuthorDTO;
import ru.akiselev.bookStore.mapper.AuthorMapper;
import ru.akiselev.bookStore.models.Author;
import ru.akiselev.bookStore.payload.exceptions.AuthorNotFoundException;
import ru.akiselev.bookStore.repositories.AuthorsRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthorsService {

    private final AuthorsRepository authorsRepository;
    private final AuthorMapper authorMapper;

    @Transactional
    public AuthorDTO create(AuthorDTO authorDTO) {
        return authorMapper.toDto(authorsRepository.save(authorMapper.toAuthor(authorDTO)));
    }

    public AuthorDTO readDto(Long id) {
        return authorsRepository.findById(id).map(authorMapper::toDto).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    public Author read(Long id) {
        return authorsRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        authorsRepository.deleteById(id);
    }

}
