package ru.akiselev.emailSender.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akiselev.emailSender.models.Author;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Long> {
}
