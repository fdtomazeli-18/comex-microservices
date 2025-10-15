package br.com.alura.produto.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CategoriaEventProducer {

    private static final String TOPIC = "categoria-events";

    @Autowired
    private KafkaTemplate<String, CategoriaEvent> kafkaTemplate;

    public void publishCategoriaCreated(String nome) {
        CategoriaEvent event = new CategoriaEvent(nome);
        kafkaTemplate.send(TOPIC, event);
    }
}