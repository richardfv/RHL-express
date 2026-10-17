package com.cibertec.msenvios.negocio;

import com.cibertec.msenvios.dto.EnvioRequest;
import com.cibertec.msenvios.dto.EnvioResponse;
import com.cibertec.msenvios.entidades.Envio;
import com.cibertec.msenvios.repositorio.EnvioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class EnvioService {

	private final EnvioRepository envioRepository;

	public EnvioService(EnvioRepository envioRepository) {
		this.envioRepository = envioRepository;
	}

	public List<EnvioResponse> listarTodos() {
		return envioRepository.findAll().stream()
				.map(this::mapToResponse)
				.toList();
	}

	public EnvioResponse obtenerPorId(Long id) {
		Envio envio = envioRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Envio no encontrado"));
		return mapToResponse(envio);
	}

	public EnvioResponse obtenerPorTracking(String codigoTracking) {
		Envio envio = envioRepository.findByCodigoTracking(codigoTracking)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tracking no encontrado"));
		return mapToResponse(envio);
	}

	public EnvioResponse registrarEnvio(EnvioRequest request) {
		String tracking = generarCodigoTracking();

		Envio envio = Envio.builder()
				.codigoTracking(tracking)
				.remitente(request.remitente())
				.dniRemitente(request.dniRemitente())
				.destinatario(request.destinatario())
				.direccionDestino(request.direccionDestino())
				.ciudadDestino(request.ciudadDestino())
				.peso(request.peso())
				.costo(request.costo())
				.estado("REGISTRADO")
				.descripcion(request.descripcion())
				.fechaRegistro(LocalDateTime.now())
				.build();

		Envio guardado = envioRepository.save(envio);
		return mapToResponse(guardado);
	}

	public EnvioResponse actualizarEstado(Long id, String nuevoEstado) {
		Envio envio = envioRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Envio no encontrado"));

		envio.setEstado(nuevoEstado);
		Envio actualizado = envioRepository.save(envio);
		return mapToResponse(actualizado);
	}

	public void eliminarEnvio(Long id) {
		Envio envio = envioRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Envio no encontrado"));
		envioRepository.delete(envio);
	}

	private String generarCodigoTracking() {
		int randomNum = ThreadLocalRandom.current().nextInt(10000, 99999);
		return "ENV-" + randomNum;
	}

	private EnvioResponse mapToResponse(Envio envio) {
		return new EnvioResponse(
				envio.getId(),
				envio.getCodigoTracking(),
				envio.getRemitente(),
				envio.getDniRemitente(),
				envio.getDestinatario(),
				envio.getDireccionDestino(),
				envio.getCiudadDestino(),
				envio.getPeso(),
				envio.getCosto(),
				envio.getEstado(),
				envio.getDescripcion(),
				envio.getFechaRegistro()
		);
	}
}
