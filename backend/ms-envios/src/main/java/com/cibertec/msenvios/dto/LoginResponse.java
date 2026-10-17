package com.cibertec.msenvios.dto;

public record LoginResponse(
		String token,
		String tokenType,
		Long expiresInMinutes,
		String username,
		String rol
) {
}
