package br.com.alura.comex.producer.users;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitProducerConfig {

    public static final String EXCHANGE_USERS = "users-actions-exchange";
    public static final String RK_USERS_CREATED = "users.actions";

    @Bean
    TopicExchange usersExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_USERS).durable(true).build();
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}