package ru.akiselev.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.akiselev.dto.RegisteredUserDTO;
import ru.akiselev.enums.KafkaTopics;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, RegisteredUserDTO> kafkaTemplate;

    public void send(KafkaTopics topic, RegisteredUserDTO registeredUserDTO) {
        kafkaTemplate.send(topic.name(), registeredUserDTO);
    }
}
