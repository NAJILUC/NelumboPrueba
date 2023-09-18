package com.nelumbo.parqueadero.service;

import com.nelumbo.parqueadero.domain.Rol;
import org.springframework.stereotype.Service;

@Service
public interface RolService {
    Rol obtenerRolPorId(Long id);
}
