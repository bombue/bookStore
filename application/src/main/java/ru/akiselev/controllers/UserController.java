package ru.akiselev.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.dto.RegisteredUserDTO;
import ru.akiselev.dto.SignInDTO;
import ru.akiselev.dto.SignUpDTO;
import ru.akiselev.payload.response.JwtResponse;
import ru.akiselev.services.UsersService;

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
