package com.cibertec.msenvios.negocio;

import com.cibertec.msenvios.config.RabbitMQConfig;
import com.cibertec.msenvios.dto.EventoTracking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class TrackingEventProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrackingEventProducer.class);

	private final RabbitTemplate rabbitTemplate;

	public TrackingEventProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void publicarEvento(EventoTracking evento) {
		try {
			rabbitTemplate.convertAndSend(
					RabbitMQConfig.COURIER_EXCHANGE,
					RabbitMQConfig.TRACKING_ROUTING_KEY,
					evento
			);
			LOGGER.info("Evento de tracking publicado en RabbitMQ: {}", evento.codigoTracking());
		} catch (Exception ex) {
			LOGGER.warn("No se pudo publicar evento en RabbitMQ: {}", ex.getMessage());
		}
	}
}
