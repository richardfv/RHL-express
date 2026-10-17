package com.cibertec.mstracking;

import com.cibertec.mstracking.entidades.HistorialTracking;
import com.cibertec.mstracking.repositorio.HistorialTrackingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class HistorialTrackingRepositoryTest {

	@Autowired
	private HistorialTrackingRepository repository;

	@BeforeEach
	void setUp() {
		repository.deleteAll();
	}

	@Test
	@DisplayName("1. Guardar hito en bitacora de tracking")
	void testGuardarHitoTracking() {
		HistorialTracking hito = HistorialTracking.builder()
				.codigoTracking("ENV-5555")
				.estado("REGISTRADO")
				.ubicacion("Agencia Central")
				.comentario("Paquete recibido")
				.fechaRegistro(LocalDateTime.now())
				.build();

		HistorialTracking guardado = repository.save(hito);

		assertNotNull(guardado.getId());
		assertEquals("ENV-5555", guardado.getCodigoTracking());
		assertEquals("REGISTRADO", guardado.getEstado());
	}

	@Test
	@DisplayName("2. Consultar historial ordenado por fecha")
	void testConsultarHistorialOrdenado() {
		repository.save(HistorialTracking.builder()
				.codigoTracking("ENV-5555")
				.estado("REGISTRADO")
				.ubicacion("Agencia Lima")
				.comentario("Paso 1")
				.fechaRegistro(LocalDateTime.now().minusHours(2))
				.build());

		repository.save(HistorialTracking.builder()
				.codigoTracking("ENV-5555")
				.estado("EN_TRANSITO")
				.ubicacion("Ruta Sur")
				.comentario("Paso 2")
				.fechaRegistro(LocalDateTime.now())
				.build());

		List<HistorialTracking> historial = repository.findByCodigoTrackingOrderByFechaRegistroAsc("ENV-5555");

		assertEquals(2, historial.size());
		assertEquals("REGISTRADO", historial.get(0).getEstado());
		assertEquals("EN_TRANSITO", historial.get(1).getEstado());
	}
}
