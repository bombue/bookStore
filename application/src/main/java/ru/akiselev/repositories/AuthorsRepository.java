package ru.akiselev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akiselev.models.Author;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Long> {
}
