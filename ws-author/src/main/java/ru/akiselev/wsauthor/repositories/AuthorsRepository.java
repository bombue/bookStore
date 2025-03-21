package ru.akiselev.wsauthor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akiselev.wsauthor.model.Author;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Long> {
}
