package ru.akiselev.bookStore.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.akiselev.bookStore.dto.RegisteredUserDTO;
import ru.akiselev.bookStore.dto.SignInDTO;
import ru.akiselev.bookStore.dto.SignUpDTO;
import ru.akiselev.bookStore.models.User;

@Mapper
public abstract class UserMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;
    public abstract User fromSignUpDto(SignUpDTO signUpDTO);

    public abstract RegisteredUserDTO toRegisteredUserDTO(User user);
}