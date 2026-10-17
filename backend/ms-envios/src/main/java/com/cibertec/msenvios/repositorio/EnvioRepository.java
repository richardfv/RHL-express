package com.cibertec.msenvios.repositorio;

import com.cibertec.msenvios.entidades.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

	Optional<Envio> findByCodigoTracking(String codigoTracking);

	List<Envio> findByEstado(String estado);
}
