package ru.akiselev.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.dto.RegisteredUserDTO;
import ru.akiselev.models.User;
import ru.akiselev.repositories.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EmailService {
    private final JavaMailSender emailSender;
    private final UserRepository userRepository;

    @KafkaListener(topics = "emailNotifications", groupId = "emailService")
    @Transactional
    public void listenEmailEvents(RegisteredUserDTO registeredUserDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(registeredUserDTO.email());
        message.setFrom("threedal@gmail.com");
        message.setSubject("registration");
        message.setText(String.format("Registration for %s : http://localhost:8080/auth/register/%s", registeredUserDTO.username(), registeredUserDTO.generatedUrl()));
        emailSender.send(message);
        // скажем что мы отправили email
        Optional<User> user = userRepository.findByUsername(registeredUserDTO.username());
        if (user.isPresent()) {
            user.get().setEmailSentFlag(true);
            userRepository.save(user.get());
        }
    }
}
