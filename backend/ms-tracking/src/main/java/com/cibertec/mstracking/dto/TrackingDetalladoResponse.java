package com.cibertec.mstracking.dto;

import com.cibertec.mstracking.entidades.HistorialTracking;

import java.util.List;

public record TrackingDetalladoResponse(
		String codigoTracking,
		String estadoActual,
		String remitente,
		String destinatario,
		String ciudadDestino,
		List<HistorialTracking> historial,
		String observacionResiliencia
) {
}
