package ru.akiselev.emailSender.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.akiselev.emailSender.dto.RegisteredUserDTO;
import ru.akiselev.emailSender.dto.SignUpDTO;
import ru.akiselev.emailSender.models.User;

@Mapper
public abstract class UserMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;
    public abstract User fromSignUpDto(SignUpDTO signUpDTO);

    public abstract RegisteredUserDTO toRegisteredUserDTO(User user);
}