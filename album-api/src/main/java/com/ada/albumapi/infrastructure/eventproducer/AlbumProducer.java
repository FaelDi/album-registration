package com.ada.albumapi.infrastructure.eventproducer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message){
        log.info("Mensagem enviada: {" + message + "}");
        String topicName = "ALBUM_CRIADO";
        kafkaTemplate.send(topicName, message);
    }

}