package com.nelumbo.parqueadero.repository;

import com.nelumbo.parqueadero.domain.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface HistoricoRepository extends JpaRepository <Historico, Long>{

    @Query(value = "SELECT placa, COUNT(placa) AS cantidad_visitas " +
            "FROM Historico " +
            "GROUP BY placa " +
            "ORDER BY cantidad_visitas DESC " +
            "LIMIT 10", nativeQuery = true)
    List<Object[]> findTop10VehiculosMasFrecuentes();

    @Query(value = "SELECT placa, COUNT(placa) AS cantidad_visitas " +
            "FROM Historico " +
            "WHERE id_parqueadero = :parqueaderoId " +
            "GROUP BY placa " +
            "ORDER BY cantidad_visitas DESC " +
            "LIMIT 10", nativeQuery = true)
    List<Object[]> findTop10VehiculosMasFrecuentes(Long parqueaderoId);

    @Query("SELECT SUM(pago) FROM Historico WHERE id_parqueadero = :parqueaderoId AND horaSalida >= :desde")
    Double gananciasHoy(Date desde, Long parqueaderoId);


}
