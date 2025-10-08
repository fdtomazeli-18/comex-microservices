package br.com.alura.comex.producer.users;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEventProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishAuthEvent(String token, String userName, Boolean active, String type) {
        AuthUserEvent event = new AuthUserEvent(token, userName, active, type);
        rabbitTemplate.convertAndSend(
            RabbitProducerConfig.EXCHANGE_USERS,
            RabbitProducerConfig.RK_USERS_CREATED,
            event
        );
    }
}