package com.cibertec.mstracking.negocio;

import com.cibertec.mstracking.client.EnvioClient;
import com.cibertec.mstracking.dto.EnvioResponse;
import com.cibertec.mstracking.dto.TrackingDetalladoResponse;
import com.cibertec.mstracking.entidades.HistorialTracking;
import com.cibertec.mstracking.repositorio.HistorialTrackingRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrackingService.class);

	private final HistorialTrackingRepository historialTrackingRepository;
	private final EnvioClient envioClient;

	public TrackingService(
			HistorialTrackingRepository historialTrackingRepository,
			EnvioClient envioClient
	) {
		this.historialTrackingRepository = historialTrackingRepository;
		this.envioClient = envioClient;
	}

	@CircuitBreaker(name = "envios", fallbackMethod = "fallbackConsultarTracking")
	public TrackingDetalladoResponse consultarTracking(String codigoTracking) {
		EnvioResponse envio = envioClient.obtenerEnvioPorTracking(codigoTracking);
		List<HistorialTracking> historial = historialTrackingRepository.findByCodigoTrackingOrderByFechaRegistroAsc(codigoTracking);

		return new TrackingDetalladoResponse(
				codigoTracking,
				envio.estado(),
				envio.remitente(),
				envio.destinatario(),
				envio.ciudadDestino(),
				historial,
				"Consulta sincronica exitosa con ms-envios via OpenFeign"
		);
	}

	public TrackingDetalladoResponse fallbackConsultarTracking(String codigoTracking, Throwable error) {
		LOGGER.warn("Circuit Breaker activado para tracking {}. Motivo: {}", codigoTracking, error.getMessage());
		List<HistorialTracking> historial = historialTrackingRepository.findByCodigoTrackingOrderByFechaRegistroAsc(codigoTracking);

		String ultimoEstado = historial.isEmpty() ? "DESCONOCIDO" : historial.get(historial.size() - 1).getEstado();

		return new TrackingDetalladoResponse(
				codigoTracking,
				ultimoEstado,
				"Informacion no disponible temporalmente",
				"Informacion no disponible temporalmente",
				"Informacion no disponible temporalmente",
				historial,
				"Respuesta en modo degradado (Circuit Breaker activado por falta de comunicacion con ms-envios)"
		);
	}
}
