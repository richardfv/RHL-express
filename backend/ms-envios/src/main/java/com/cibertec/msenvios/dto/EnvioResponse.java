package com.cibertec.msenvios.dto;

import java.time.LocalDateTime;

public record EnvioResponse(
		Long id,
		String codigoTracking,
		String remitente,
		String dniRemitente,
		String destinatario,
		String direccionDestino,
		String ciudadDestino,
		Double peso,
		Double costo,
		String estado,
		String descripcion,
		LocalDateTime fechaRegistro
) {
}
