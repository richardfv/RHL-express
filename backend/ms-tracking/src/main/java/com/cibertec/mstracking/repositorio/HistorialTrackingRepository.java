package com.cibertec.mstracking.repositorio;

import com.cibertec.mstracking.entidades.HistorialTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialTrackingRepository extends JpaRepository<HistorialTracking, Long> {

	List<HistorialTracking> findByCodigoTrackingOrderByFechaRegistroAsc(String codigoTracking);

	long countByCodigoTracking(String codigoTracking);
}
