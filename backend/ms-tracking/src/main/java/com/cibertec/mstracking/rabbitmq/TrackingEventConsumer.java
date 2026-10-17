package com.cibertec.mstracking.rabbitmq;

import com.cibertec.mstracking.config.RabbitMQConfig;
import com.cibertec.mstracking.dto.EventoTracking;
import com.cibertec.mstracking.entidades.HistorialTracking;
import com.cibertec.mstracking.repositorio.HistorialTrackingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TrackingEventConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrackingEventConsumer.class);

	private final HistorialTrackingRepository historialTrackingRepository;

	public TrackingEventConsumer(HistorialTrackingRepository historialTrackingRepository) {
		this.historialTrackingRepository = historialTrackingRepository;
	}

	@RabbitListener(queues = RabbitMQConfig.TRACKING_QUEUE)
	public void procesarEventoTracking(EventoTracking evento) {
		HistorialTracking registro = HistorialTracking.builder()
				.codigoTracking(evento.codigoTracking())
				.estado(evento.estado())
				.ubicacion(evento.ubicacion())
				.comentario(evento.comentario())
				.fechaRegistro(evento.fecha() != null ? evento.fecha() : LocalDateTime.now())
				.build();

		historialTrackingRepository.save(registro);
		LOGGER.info("Evento procesado y registrado en bitacora. Tracking: {}, Estado: {}",
				evento.codigoTracking(), evento.estado());
	}
}
