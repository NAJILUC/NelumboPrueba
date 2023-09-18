package com.nelumbo.parqueadero.repository;

import com.nelumbo.parqueadero.domain.ParqueaderoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParqueaderoVehiculoRepository extends JpaRepository<ParqueaderoVehiculo, Long> {

    Optional<ParqueaderoVehiculo> findByVehiculoId(Long id);
}
