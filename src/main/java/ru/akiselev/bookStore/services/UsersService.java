package ru.akiselev.bookStore.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.bookStore.dto.SignUpDTO;
import ru.akiselev.bookStore.enums.Role;
import ru.akiselev.bookStore.mapper.UserMapper;
import ru.akiselev.bookStore.models.User;
import ru.akiselev.bookStore.repositories.UserRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UsersService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public void create(SignUpDTO signUpDTO) {
        User user = userMapper.fromSignUpDto(signUpDTO);
        user.setRole(Role.GUEST);
        user.setCreatedDate(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
