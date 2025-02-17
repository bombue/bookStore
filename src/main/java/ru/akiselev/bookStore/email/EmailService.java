package ru.akiselev.bookStore.email;

import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.bookStore.dto.RegisteredUserDTO;
import ru.akiselev.bookStore.mapper.UserMapper;
import ru.akiselev.bookStore.models.User;
import ru.akiselev.bookStore.payload.exceptions.UserNotFoundException;
import ru.akiselev.bookStore.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EmailService {
    private final JavaMailSender emailSender;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public void sendRegistrationEmail(RegisteredUserDTO registeredUserDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(registeredUserDTO.email());
        message.setFrom("threedal@gmail.com");
        message.setSubject("registration");
        message.setText(String.format("Registration for %s : http://localhost:8080/auth/register/%s", registeredUserDTO.username(), registeredUserDTO.generatedUrl()));
        emailSender.send(message);
        // скажем что мы отправили email
        User user = userRepository.findByUsername(registeredUserDTO.username()).orElseThrow(() -> new UserNotFoundException(registeredUserDTO.username()));
        user.setEmailSentFlag(true);
        userRepository.save(user);
    }

    @Scheduled(fixedDelay = 2000)
    @Transactional
    public void pushUnsentEmails() {
        List<User> usersWithUnsentEmails = userRepository.findAllByEmailSentFlag(false);
        usersWithUnsentEmails.stream().map(userMapper::toRegisteredUserDTO).forEach(this::sendRegistrationEmail);
    }
}
