package com.cibertec.msenvios.entidades;

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
@Table(name = "envios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Envio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 30)
	private String codigoTracking;

	@Column(nullable = false, length = 120)
	private String remitente;

	@Column(nullable = false, length = 20)
	private String dniRemitente;

	@Column(nullable = false, length = 120)
	private String destinatario;

	@Column(nullable = false, length = 200)
	private String direccionDestino;

	@Column(nullable = false, length = 80)
	private String ciudadDestino;

	@Column(nullable = false)
	private Double peso;

	@Column(nullable = false)
	private Double costo;

	@Column(nullable = false, length = 30)
	private String estado;

	@Column(length = 250)
	private String descripcion;

	@Column(nullable = false)
	private LocalDateTime fechaRegistro;
}
