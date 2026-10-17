package com.cibertec.msenvios.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("1. Login exitoso con credenciales correctas retorna 200 OK y token")
	void testLoginExitoso() throws Exception {
		Map<String, String> body = Map.of(
				"username", "admin",
				"password", "admin123"
		);

		mockMvc.perform(post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(body)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").exists())
				.andExpect(jsonPath("$.tokenType").value("Bearer"))
				.andExpect(jsonPath("$.username").value("admin"))
				.andExpect(jsonPath("$.rol").value("ADMIN"));
	}

	@Test
	@DisplayName("2. Login con contrasenia incorrecta retorna 401 Unauthorized")
	void testLoginPasswordIncorrecto() throws Exception {
		Map<String, String> body = Map.of(
				"username", "admin",
				"password", "password_invalido"
		);

		mockMvc.perform(post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(body)))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("3. Login con campos vacios retorna 400 Bad Request")
	void testLoginCamposVacios() throws Exception {
		Map<String, String> body = Map.of(
				"username", "",
				"password", ""
		);

		mockMvc.perform(post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(body)))
				.andExpect(status().isBadRequest());
	}
}
