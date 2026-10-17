package com.cibertec.mstracking.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_tracking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialTracking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 30)
	private String codigoTracking;

	@Column(nullable = false, length = 30)
	private String estado;

	@Column(nullable = false, length = 120)
	private String ubicacion;

	@Column(length = 250)
	private String comentario;

	@Column(nullable = false)
	private LocalDateTime fechaRegistro;
}
