package br.com.alura.produto.consumer.users;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConsumerConfig {

    public static final String EXCHANGE_USERS = "users-actions-exchange"; // topic
    public static final String QUEUE_USERS_CREATED = "users.actions.q";
    public static final String QUEUE_USERS_CREATED_DLQ = "users.actions.dlq";
    public static final String RK_USERS_CREATED = "users.actions";

    @Bean
    TopicExchange usersExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_USERS).durable(true).build();
    }

    @Bean
    Queue usersCreatedQueue() {
        return QueueBuilder.durable(QUEUE_USERS_CREATED)
                .withArgument("x-dead-letter-exchange", EXCHANGE_USERS)
                .withArgument("x-dead-letter-routing-key", RK_USERS_CREATED + ".dlq")
                .build();
    }

    @Bean
    Queue usersCreatedDlq() {
        return QueueBuilder.durable(QUEUE_USERS_CREATED_DLQ).build();
    }

    @Bean
    Binding usersCreatedBinding() {
        return BindingBuilder.bind(usersCreatedQueue())
                .to(usersExchange())
                .with(RK_USERS_CREATED);
    }

    @Bean
    Binding usersCreatedDlqBinding() {
        return BindingBuilder.bind(usersCreatedDlq())
                .to(usersExchange())
                .with(RK_USERS_CREATED + ".dlq");
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter converter
    ) {
        var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);

        // Concorrência e prefetch (ajuste conforme carga)
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(4);
        factory.setPrefetchCount(50);
        // factory.setDefaultRequeueRejected(false);
        return factory;
    }
}
