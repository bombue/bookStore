package ru.akiselev.bookStore.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.bookStore.dto.SignInDTO;
import ru.akiselev.bookStore.dto.SignUpDTO;
import ru.akiselev.bookStore.email.EmailService;
import ru.akiselev.bookStore.payload.response.JwtResponse;
import ru.akiselev.bookStore.security.UserDetailsImpl;
import ru.akiselev.bookStore.security.jwt.JwtUtils;
import ru.akiselev.bookStore.services.UsersService;
import ru.akiselev.bookStore.payload.response.ErrorResponse;
import ru.akiselev.bookStore.payload.exceptions.EmailAlreadyExistsException;
import ru.akiselev.bookStore.payload.exceptions.UserAlreadyExistsException;
import ru.akiselev.bookStore.payload.exceptions.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UsersService usersService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpDTO signUpDTO) {
        usersService.create(signUpDTO);
        emailService.sendSimpleEmail(signUpDTO.email(), "registration", "bookStore registration");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignInDTO signInDTO) {
        return ResponseEntity.ok(usersService.signIn(signInDTO));
    }
}
