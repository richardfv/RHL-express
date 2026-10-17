package com.cibertec.msenvios.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
		@NotBlank(message = "El username es obligatorio")
		String username,

		@NotBlank(message = "El password es obligatorio")
		String password
) {
}
