package com.nelumbo.parqueadero.repository;

import com.nelumbo.parqueadero.domain.ParqueaderoVehiculo;
import com.nelumbo.parqueadero.dto.response.VehiculoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParqueaderoVehiculoRepository extends JpaRepository<ParqueaderoVehiculo, Long> {

    Optional<ParqueaderoVehiculo> findByVehiculoId(Long id);

    @Query(value = "SELECT * FROM parqueadero_vehiculo WHERE parqueadero_id = :id", nativeQuery = true)
    List<ParqueaderoVehiculo> findAllByParqueaderoId(Long id);
    @Query(value = "SELECT COUNT(*) FROM parqueadero_vehiculo WHERE parqueadero_id = :id", nativeQuery = true)
    Long countAllByParqueaderoId(Long id);

}
