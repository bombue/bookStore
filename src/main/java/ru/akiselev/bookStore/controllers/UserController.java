package ru.akiselev.bookStore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.akiselev.bookStore.dto.SignInDTO;
import ru.akiselev.bookStore.dto.SignUpDTO;
import ru.akiselev.bookStore.mapper.UserInfoMapper;
import ru.akiselev.bookStore.payload.response.JwtResponse;
import ru.akiselev.bookStore.security.UserDetailsImpl;
import ru.akiselev.bookStore.security.services.UserDetailsServiceImpl;
import ru.akiselev.bookStore.security.jwt.JwtUtils;
import ru.akiselev.bookStore.services.UsersService;
import ru.akiselev.bookStore.payload.response.AuthErrorResponse;
import ru.akiselev.bookStore.payload.exceptions.EmailAlreadyExistsException;
import ru.akiselev.bookStore.payload.exceptions.UserAlreadyExistsException;
import ru.akiselev.bookStore.payload.exceptions.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserInfoMapper userInfoMapper;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UsersService usersService;

    @Autowired
    public UserController(UserInfoMapper userInfoMapper, JwtUtils jwtUtils, AuthenticationManager authenticationManager, UsersService usersService) {
        this.userInfoMapper = userInfoMapper;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.usersService = usersService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpDTO signUpDTO) {
        if (usersService.existsByUsername(signUpDTO.getUsername())) {
            throw new UserAlreadyExistsException();
        }
        if (usersService.existsByEmail(signUpDTO.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        usersService.create(userInfoMapper.fromSignUpDto(signUpDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignInDTO signInDTO) {
        if (!usersService.existsByUsername(signInDTO.getUsername())) {
            throw new UserNotFoundException();
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDTO.getUsername(), signInDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
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
                "User is already exists!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<AuthErrorResponse> handleException(EmailAlreadyExistsException e) {
        AuthErrorResponse response = new AuthErrorResponse(
                "Email is already exists!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
