package br.com.alura.produto.consumer.users;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class UsersActionsListener {

    @RabbitListener(queues = RabbitConsumerConfig.QUEUE_USERS_CREATED)
    public void onUserAction(AuthUserEvent event,
                             @Header(name = "x-correlation-id", required = false) String correlationId) {
        try {
            System.out.printf("[USERS][EVENT] action=%s username=%s corrId=%s%n " + event,
                    event.getType(), event.getUserName(), correlationId);

            // aqui fica a nossa implementação para registrar no banco de dados por exemplo...

        } catch (IllegalArgumentException e) {
            throw new AmqpRejectAndDontRequeueException("Erro de negócio: " + e.getMessage(), e);
        } catch (Exception e) {
            throw e;
        }
    }
}

