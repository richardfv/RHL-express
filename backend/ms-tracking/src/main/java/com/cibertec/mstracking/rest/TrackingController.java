package com.cibertec.mstracking.rest;

import com.cibertec.mstracking.dto.TrackingDetalladoResponse;
import com.cibertec.mstracking.negocio.TrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

	private final TrackingService trackingService;

	public TrackingController(TrackingService trackingService) {
		this.trackingService = trackingService;
	}

	@GetMapping("/{codigoTracking}")
	public ResponseEntity<TrackingDetalladoResponse> consultarTracking(@PathVariable String codigoTracking) {
		return ResponseEntity.ok(trackingService.consultarTracking(codigoTracking));
	}
}
