package ru.akiselev.bookStore.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.akiselev.bookStore.dto.RegisteredUserDTO;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender emailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("threedal@gmail.com");
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendRegistrationEmail(RegisteredUserDTO registeredUserDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(registeredUserDTO.email());
        message.setFrom("threedal@gmail.com");
        message.setSubject("registration");
        message.setText(String.format("Registration for %s : http://localhost:8080/auth/register/%s", registeredUserDTO.username(), registeredUserDTO.generatedUrl()));
        emailSender.send(message);
    }
}
