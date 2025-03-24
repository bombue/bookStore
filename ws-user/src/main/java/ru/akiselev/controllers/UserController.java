package ru.akiselev.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.dto.RegisteredUserDTO;
import ru.akiselev.dto.SignInDTO;
import ru.akiselev.dto.SignUpDTO;
import ru.akiselev.payload.response.JwtResponse;
import ru.akiselev.services.KafkaService;
import ru.akiselev.services.UsersService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UsersService usersService;
    private final KafkaService kafkaService;
    private final Environment env;

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signup(@Valid @RequestBody SignUpDTO signUpDTO) {
        RegisteredUserDTO registeredUserDTO = usersService.create(signUpDTO);
//        kafkaService.send(KafkaTopics.emailNotifications, registeredUserDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@Valid @RequestBody SignInDTO signInDTO) {
        return ResponseEntity.ok(usersService.signIn(signInDTO));
    }

    @PostMapping("/register/{generatedUrl}")
    private ResponseEntity<HttpStatus> finishRegistration(@PathVariable("generatedUrl") String generatedUrl) {
        usersService.finishRegistration(generatedUrl);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/check")
    public String status() {
        return String.format("Working on port %s, with %s", env.getProperty("local.server.port"), env.getProperty("jwt.secret"));
    }
}
