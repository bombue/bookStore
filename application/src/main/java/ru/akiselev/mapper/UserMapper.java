package ru.akiselev.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.akiselev.dto.RegisteredUserDTO;
import ru.akiselev.dto.SignUpDTO;
import ru.akiselev.models.User;

@Mapper
public abstract class UserMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;
    public abstract User fromSignUpDto(SignUpDTO signUpDTO);

    public abstract RegisteredUserDTO toRegisteredUserDTO(User user);
}