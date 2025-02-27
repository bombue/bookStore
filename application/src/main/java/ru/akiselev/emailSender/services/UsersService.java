package ru.akiselev.emailSender.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.emailSender.dto.RegisteredUserDTO;
import ru.akiselev.emailSender.dto.SignInDTO;
import ru.akiselev.emailSender.dto.SignUpDTO;
import ru.akiselev.emailSender.enums.Role;
import ru.akiselev.emailSender.mapper.UserMapper;
import ru.akiselev.emailSender.models.User;
import ru.akiselev.emailSender.payload.exceptions.EmailAlreadyExistsException;
import ru.akiselev.emailSender.payload.exceptions.UserAlreadyExistsException;
import ru.akiselev.emailSender.payload.exceptions.UserNotFoundException;
import ru.akiselev.emailSender.payload.exceptions.WrongUrlAuthException;
import ru.akiselev.emailSender.payload.response.JwtResponse;
import ru.akiselev.emailSender.repositories.UserRepository;
import ru.akiselev.emailSender.security.UserDetailsImpl;
import ru.akiselev.emailSender.security.jwt.JwtUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UsersService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Transactional
    public RegisteredUserDTO create(SignUpDTO signUpDTO) {
        if (existsByUsername(signUpDTO.username())) {
            throw new UserAlreadyExistsException(signUpDTO.username());
        }
        if (existsByEmail(signUpDTO.email())) {
            throw new EmailAlreadyExistsException(signUpDTO.email());
        }
        User user = userMapper.fromSignUpDto(signUpDTO);
        user.setRole(Role.GUEST);
        user.setCreatedDate(LocalDateTime.now());
        user.setGeneratedUrl(UUID.randomUUID().toString());
        user.setEmailSentFlag(false);
        userRepository.save(user);
        return userMapper.toRegisteredUserDTO(user);
    }

    public JwtResponse signIn(SignInDTO signInDTO) {
        if (!existsByUsername(signInDTO.username())) {
            throw new UserNotFoundException(signInDTO.username());
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDTO.username(), signInDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Transactional
    public void finishRegistration(String generatedUrl) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findByGeneratedUrl(generatedUrl).orElseThrow(() -> new WrongUrlAuthException(generatedUrl));

        // проверим что по ссылке пришел верный пользователь и что есть он
        if (!user.getUsername().equals(userDetails.getUsername())) {
            throw new WrongUrlAuthException(generatedUrl, user.getUsername());
        }

        user.setRole(Role.USER);
        user.setGeneratedUrl(null);
        userRepository.save(user);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
