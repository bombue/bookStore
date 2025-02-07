package ru.akiselev.bookStore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.bookStore.dto.SignInDTO;
import ru.akiselev.bookStore.dto.SignUpDTO;
import ru.akiselev.bookStore.mapper.UserInfoMapper;
import ru.akiselev.bookStore.security.services.UserDetailsServiceImpl;
import ru.akiselev.bookStore.security.jwt.JwtUtils;
import ru.akiselev.bookStore.untils.AuthErrorResponse;
import ru.akiselev.bookStore.untils.UserAlreadyExistsException;
import ru.akiselev.bookStore.untils.UserNotFoundException;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final UserInfoMapper userInfoMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserController(UserDetailsServiceImpl userDetailsServiceImpl, UserInfoMapper userInfoMapper, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.userInfoMapper = userInfoMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signup(@RequestBody SignUpDTO signUpDTO) {
        if (userDetailsServiceImpl.existsByUsername(signUpDTO.getUsername())) {

        }
        userDetailsServiceImpl.signup(userInfoMapper.fromSignUpDto(signUpDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<HttpStatus> signin(@RequestBody SignInDTO signInDTO) {
        // todo
        // валидация по пользователю-почте
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<AuthErrorResponse> handleException(UserNotFoundException e) {
        AuthErrorResponse response = new AuthErrorResponse(
                "User not found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<AuthErrorResponse> handleException(UserAlreadyExistsException e) {
        AuthErrorResponse response = new AuthErrorResponse(
                "User already exists!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
