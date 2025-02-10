package ru.akiselev.bookStore.mapper;

import org.mapstruct.Mapper;
import ru.akiselev.bookStore.dto.SignInDTO;
import ru.akiselev.bookStore.dto.SignUpDTO;
import ru.akiselev.bookStore.models.User;

@Mapper
public interface UserMapper {
    SignUpDTO toSignUpDto(User user);
    User fromSignUpDto(SignUpDTO signUpDTO);
    SignInDTO toSignInDto(User user);
    User fromSignIn(SignInDTO signInDTO);
}
