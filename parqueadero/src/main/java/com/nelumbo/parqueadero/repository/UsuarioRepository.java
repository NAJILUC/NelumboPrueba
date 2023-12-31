package com.nelumbo.parqueadero.repository;

import com.nelumbo.parqueadero.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

        Usuario findByCorreo(String correo);
}
