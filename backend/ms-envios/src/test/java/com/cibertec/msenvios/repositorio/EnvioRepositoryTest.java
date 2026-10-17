package com.cibertec.msenvios.repositorio;

import com.cibertec.msenvios.entidades.Envio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EnvioRepositoryTest {

	@Autowired
	private EnvioRepository envioRepository;

	private Envio envioBase;

	@BeforeEach
	void setUp() {
		envioRepository.deleteAll();

		envioBase = Envio.builder()
				.codigoTracking("ENV-9999")
				.remitente("Mario Vargas")
				.dniRemitente("08765432")
				.destinatario("Rosa Prado")
				.direccionDestino("Av. Brasil 2040")
				.ciudadDestino("Lima")
				.peso(3.2)
				.costo(30.0)
				.estado("REGISTRADO")
				.descripcion("Muestras de laboratorio")
				.fechaRegistro(LocalDateTime.now())
				.build();
	}

	@Test
	@DisplayName("1. Prueba de insercion (Create / Save)")
	void testInsertarEnvio() {
		Envio guardado = envioRepository.save(envioBase);

		assertNotNull(guardado.getId());
		assertEquals("ENV-9999", guardado.getCodigoTracking());
		assertEquals("Mario Vargas", guardado.getRemitente());
		assertEquals(30.0, guardado.getCosto());
	}

	@Test
	@DisplayName("2. Prueba de lectura (Read / FindById & FindAll)")
	void testConsultarEnvios() {
		Envio guardado = envioRepository.save(envioBase);

		Optional<Envio> encontradoPorId = envioRepository.findById(guardado.getId());
		assertTrue(encontradoPorId.isPresent());
		assertEquals("ENV-9999", encontradoPorId.get().getCodigoTracking());

		List<Envio> lista = envioRepository.findAll();
		assertEquals(1, lista.size());

		Optional<Envio> encontradoPorTracking = envioRepository.findByCodigoTracking("ENV-9999");
		assertTrue(encontradoPorTracking.isPresent());
		assertEquals("Rosa Prado", encontradoPorTracking.get().getDestinatario());
	}

	@Test
	@DisplayName("3. Prueba de actualizacion (Update / Save)")
	void testActualizarEnvio() {
		Envio guardado = envioRepository.save(envioBase);

		guardado.setEstado("EN_TRANSITO");
		guardado.setCosto(38.5);
		guardado.setDireccionDestino("Av. Salaverry 1100");

		Envio actualizado = envioRepository.save(guardado);

		assertEquals("EN_TRANSITO", actualizado.getEstado());
		assertEquals(38.5, actualizado.getCosto());
		assertEquals("Av. Salaverry 1100", actualizado.getDireccionDestino());
	}

	@Test
	@DisplayName("4. Prueba de eliminacion (Delete)")
	void testEliminarEnvio() {
		Envio guardado = envioRepository.save(envioBase);
		Long id = guardado.getId();

		envioRepository.deleteById(id);

		Optional<Envio> resultado = envioRepository.findById(id);
		assertTrue(resultado.isEmpty());
		assertEquals(0, envioRepository.count());
	}
}
