package com.nelumbo.parqueadero.repository;

import com.nelumbo.parqueadero.domain.Parqueadero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParqueaderoRepository extends JpaRepository<Parqueadero, Long> {

    @Query("SELECT p FROM Parqueadero p WHERE p.usuario.id = :idUsuario")
    List<Parqueadero> findByUserId(Long idUsuario);
}
