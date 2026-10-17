package com.cibertec.msenvios.negocio;

import com.cibertec.msenvios.dto.LoginRequest;
import com.cibertec.msenvios.dto.LoginResponse;
import com.cibertec.msenvios.entidades.Usuario;
import com.cibertec.msenvios.repositorio.UsuarioRepository;
import com.cibertec.msenvios.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public AuthService(
			UsuarioRepository usuarioRepository,
			PasswordEncoder passwordEncoder,
			JwtService jwtService
	) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	public LoginResponse login(LoginRequest request) {
		Usuario usuario = usuarioRepository.findByUsername(request.username())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas"));

		if (!passwordEncoder.matches(request.password(), usuario.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas");
		}

		if (Boolean.FALSE.equals(usuario.getActivo())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario inactivo");
		}

		String token = jwtService.generateToken(usuario);
		return new LoginResponse(token, "Bearer", jwtService.getExpirationMinutes(), usuario.getUsername(), usuario.getRol());
	}
}
