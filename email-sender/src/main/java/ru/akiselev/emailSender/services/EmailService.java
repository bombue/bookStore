package ru.akiselev.emailSender.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akiselev.emailSender.dto.RegisteredUserDTO;
import ru.akiselev.emailSender.models.User;
import ru.akiselev.emailSender.repositories.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EmailService {
    private final JavaMailSender emailSender;
    private final UserRepository userRepository;

    @KafkaListener(topics = "emailNotifications", containerFactory = "kafkaListenerContainerFactory")
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
