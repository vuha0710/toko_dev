package io.konekto.backoffice.config;

import io.konekto.backoffice.config.properties.BackOfficeProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Autowired
    BackOfficeProperties backOfficeProperties;

	@Bean
    Queue businessQueue() {
        return new Queue(backOfficeProperties.getRabbitmq().getBusinessQueue(), true);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(backOfficeProperties.getRabbitmq().getExchange());
    }

    @Bean
    Binding businessBinding(Queue businessQueue, TopicExchange exchange) {
        return BindingBuilder.bind(businessQueue).to(exchange).with(backOfficeProperties.getRabbitmq().getBusinessRoutingKey());
    }

    @Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}
