package ru.akiselev.bookStore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.bookStore.models.Author;
import ru.akiselev.bookStore.repositories.AuthorsRepository;

@Service
@Transactional(readOnly = true)
public class AuthorsService {

    private final AuthorsRepository authorsRepository;

    @Autowired
    public AuthorsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    @Transactional
    public void create(Author author) {
        this.authorsRepository.save(author);
    }

    public Author read(Long id) {
        return this.authorsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Long id) {
        this.authorsRepository.deleteById(id);
    }

}
