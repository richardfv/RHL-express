package com.cibertec.msenvios.dto;

import jakarta.validation.constraints.NotBlank;

public record ActualizarEstadoRequest(
		@NotBlank(message = "El nuevo estado es obligatorio")
		String estado
) {
}
