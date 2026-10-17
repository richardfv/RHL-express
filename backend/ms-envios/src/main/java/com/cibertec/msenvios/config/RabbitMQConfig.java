package com.cibertec.msenvios.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String COURIER_EXCHANGE = "courier.exchange";
	public static final String TRACKING_QUEUE = "tracking.queue";
	public static final String TRACKING_ROUTING_KEY = "tracking.event";

	@Bean
	public DirectExchange courierExchange() {
		return new DirectExchange(COURIER_EXCHANGE);
	}

	@Bean
	public Queue trackingQueue() {
		return new Queue(TRACKING_QUEUE, true);
	}

	@Bean
	public Binding trackingBinding(Queue trackingQueue, DirectExchange courierExchange) {
		return BindingBuilder.bind(trackingQueue)
				.to(courierExchange)
				.with(TRACKING_ROUTING_KEY);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
