package com.cibertec.msenvios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EnvioRequest(
		@NotBlank(message = "El remitente es obligatorio")
		String remitente,

		@NotBlank(message = "El DNI del remitente es obligatorio")
		String dniRemitente,

		@NotBlank(message = "El destinatario es obligatorio")
		String destinatario,

		@NotBlank(message = "La direccion de destino es obligatoria")
		String direccionDestino,

		@NotBlank(message = "La ciudad de destino es obligatoria")
		String ciudadDestino,

		@NotNull(message = "El peso es obligatorio")
		@Positive(message = "El peso debe ser mayor a cero")
		Double peso,

		@NotNull(message = "El costo es obligatorio")
		@Positive(message = "El costo debe ser mayor a cero")
		Double costo,

		String descripcion
) {
}
