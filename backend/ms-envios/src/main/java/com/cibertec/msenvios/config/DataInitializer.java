package com.cibertec.msenvios.config;

import com.cibertec.msenvios.entidades.Envio;
import com.cibertec.msenvios.entidades.Usuario;
import com.cibertec.msenvios.repositorio.EnvioRepository;
import com.cibertec.msenvios.repositorio.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

	@Bean
	CommandLineRunner seedData(
			UsuarioRepository usuarioRepository,
			EnvioRepository envioRepository,
			PasswordEncoder passwordEncoder
	) {
		return args -> {
			if (usuarioRepository.count() == 0) {
				usuarioRepository.save(Usuario.builder()
						.username("admin")
						.email("admin@courier.com")
						.password(passwordEncoder.encode("admin123"))
						.rol("ADMIN")
						.activo(true)
						.build());

				usuarioRepository.save(Usuario.builder()
						.username("operador")
						.email("operador@courier.com")
						.password(passwordEncoder.encode("operador123"))
						.rol("OPERADOR")
						.activo(true)
						.build());
			}

			if (envioRepository.count() == 0) {
				envioRepository.save(Envio.builder()
						.codigoTracking("ENV-1001")
						.remitente("Juan Perez")
						.dniRemitente("45892314")
						.destinatario("Maria Lopez")
						.direccionDestino("Av. Arequipa 1250, Miraflores")
						.ciudadDestino("Lima")
						.peso(2.5)
						.costo(25.0)
						.estado("REGISTRADO")
						.descripcion("Documentos y muestras comerciales")
						.fechaRegistro(LocalDateTime.now().minusDays(2))
						.build());

				envioRepository.save(Envio.builder()
						.codigoTracking("ENV-1002")
						.remitente("Carlos Ruiz")
						.dniRemitente("70123456")
						.destinatario("Ana Gomez")
						.direccionDestino("Calle Mercaderes 305")
						.ciudadDestino("Arequipa")
						.peso(5.0)
						.costo(45.0)
						.estado("EN_TRANSITO")
						.descripcion("Paquete electronico fragil")
						.fechaRegistro(LocalDateTime.now().minusDays(1))
						.build());

				envioRepository.save(Envio.builder()
						.codigoTracking("ENV-1003")
						.remitente("Pedro Diaz")
						.dniRemitente("10876543")
						.destinatario("Sofia Castro")
						.direccionDestino("Av. Larco 450")
						.ciudadDestino("Trujillo")
						.peso(1.2)
						.costo(18.0)
						.estado("ENTREGADO")
						.descripcion("Ropa y accesorios")
						.fechaRegistro(LocalDateTime.now().minusHours(5))
						.build());
			}
		};
	}
}
