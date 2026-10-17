package com.cibertec.msenvios.rest;

import com.cibertec.msenvios.dto.ActualizarEstadoRequest;
import com.cibertec.msenvios.dto.EnvioRequest;
import com.cibertec.msenvios.dto.EnvioResponse;
import com.cibertec.msenvios.negocio.EnvioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

	private final EnvioService envioService;

	public EnvioController(EnvioService envioService) {
		this.envioService = envioService;
	}

	@GetMapping
	public ResponseEntity<List<EnvioResponse>> listarTodos() {
		return ResponseEntity.ok(envioService.listarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EnvioResponse> obtenerPorId(@PathVariable Long id) {
		return ResponseEntity.ok(envioService.obtenerPorId(id));
	}

	@GetMapping("/tracking/{codigoTracking}")
	public ResponseEntity<EnvioResponse> obtenerPorTracking(@PathVariable String codigoTracking) {
		return ResponseEntity.ok(envioService.obtenerPorTracking(codigoTracking));
	}

	@PostMapping
	public ResponseEntity<EnvioResponse> registrarEnvio(@Valid @RequestBody EnvioRequest request) {
		EnvioResponse nuevoEnvio = envioService.registrarEnvio(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEnvio);
	}

	@PutMapping("/{id}/estado")
	public ResponseEntity<EnvioResponse> actualizarEstado(
			@PathVariable Long id,
			@Valid @RequestBody ActualizarEstadoRequest request
	) {
		return ResponseEntity.ok(envioService.actualizarEstado(id, request.estado()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarEnvio(@PathVariable Long id) {
		envioService.eliminarEnvio(id);
		return ResponseEntity.noContent().build();
	}
}
