package com.cibertec.mstracking.config;

import com.cibertec.mstracking.entidades.HistorialTracking;
import com.cibertec.mstracking.repositorio.HistorialTrackingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class TrackingDataInitializer {

	@Bean
	CommandLineRunner seedTrackingData(HistorialTrackingRepository repository) {
		return args -> {
			if (repository.count() == 0) {
				repository.save(HistorialTracking.builder()
						.codigoTracking("ENV-1001")
						.estado("REGISTRADO")
						.ubicacion("Agencia Miraflores, Lima")
						.comentario("Paquete recepcionado y documentado en ventanilla")
						.fechaRegistro(LocalDateTime.now().minusDays(2))
						.build());

				repository.save(HistorialTracking.builder()
						.codigoTracking("ENV-1002")
						.estado("REGISTRADO")
						.ubicacion("Agencia Central, Trujillo")
						.comentario("Paquete recibido para despacho nacional")
						.fechaRegistro(LocalDateTime.now().minusDays(1).minusHours(4))
						.build());

				repository.save(HistorialTracking.builder()
						.codigoTracking("ENV-1002")
						.estado("EN_TRANSITO")
						.ubicacion("Centro de Distribucion Arequipa")
						.comentario("En transito hacia terminal terrestre")
						.fechaRegistro(LocalDateTime.now().minusDays(1))
						.build());

				repository.save(HistorialTracking.builder()
						.codigoTracking("ENV-1003")
						.estado("REGISTRADO")
						.ubicacion("Agencia Principal, Lima")
						.comentario("Paquete recibido")
						.fechaRegistro(LocalDateTime.now().minusHours(8))
						.build());

				repository.save(HistorialTracking.builder()
						.codigoTracking("ENV-1003")
						.estado("EN_TRANSITO")
						.ubicacion("Ruta Trujillo Norte")
						.comentario("En unidad de reparto local")
						.fechaRegistro(LocalDateTime.now().minusHours(3))
						.build());

				repository.save(HistorialTracking.builder()
						.codigoTracking("ENV-1003")
						.estado("ENTREGADO")
						.ubicacion("Direccion de destino, Trujillo")
						.comentario("Entregado conforme a destinatario")
						.fechaRegistro(LocalDateTime.now().minusHours(1))
						.build());
			}
		};
	}
}
