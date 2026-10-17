package com.cibertec.mstracking.dto;

import java.time.LocalDateTime;

public record EventoTracking(
		String codigoTracking,
		String estado,
		String ubicacion,
		String comentario,
		LocalDateTime fecha
) {
}
