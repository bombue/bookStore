package ru.akiselev.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.akiselev.bookStore.models.UserInfo;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);
}
