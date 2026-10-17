package com.cibertec.mstracking.client;

import com.cibertec.mstracking.dto.EnvioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
		name = "ms-envios",
		url = "${app.envios-service.url:http://localhost:8081}"
)
public interface EnvioClient {

	@GetMapping("/api/envios/tracking/{codigoTracking}")
	EnvioResponse obtenerEnvioPorTracking(@PathVariable("codigoTracking") String codigoTracking);
}
