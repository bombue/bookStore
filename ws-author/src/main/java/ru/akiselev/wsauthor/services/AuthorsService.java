package ru.akiselev.wsauthor.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.wsauthor.dto.AuthorDTO;
import ru.akiselev.wsauthor.exceptions.AuthorNotFoundException;
import ru.akiselev.wsauthor.mapper.AuthorMapper;
import ru.akiselev.wsauthor.model.Author;
import ru.akiselev.wsauthor.repositories.AuthorsRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthorsService {

    private final AuthorsRepository authorsRepository;
    private final AuthorMapper authorMapper;

    @Transactional
    public AuthorDTO create(AuthorDTO authorDTO) {
        Author author = authorMapper.toAuthor(authorDTO);
        authorsRepository.save(author);
        return authorMapper.toDto(author);
    }

    public AuthorDTO read(Long id) {
        return authorsRepository.findById(id).map(authorMapper::toDto).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        authorsRepository.deleteById(id);
    }

}
