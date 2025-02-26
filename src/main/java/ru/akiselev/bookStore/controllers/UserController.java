package ru.akiselev.bookStore.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.bookStore.dto.RegisteredUserDTO;
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

    private final UsersService usersService;
    private final KafkaTemplate<String, RegisteredUserDTO> kafkaTemplate;

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signup(@RequestBody SignUpDTO signUpDTO) {
        RegisteredUserDTO registeredUserDTO = usersService.create(signUpDTO);
        kafkaTemplate.send("emailNotifications", registeredUserDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody SignInDTO signInDTO) {
        return ResponseEntity.ok(usersService.signIn(signInDTO));
    }

    @PostMapping("/register/{generatedUrl}")
    private ResponseEntity<HttpStatus> finishRegistration(@PathVariable("generatedUrl") String generatedUrl) {
        usersService.finishRegistration(generatedUrl);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
