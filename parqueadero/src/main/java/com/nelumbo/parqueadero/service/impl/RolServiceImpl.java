package com.nelumbo.parqueadero.service.impl;

import com.nelumbo.parqueadero.domain.Rol;
import com.nelumbo.parqueadero.exception.NotFoundException;
import com.nelumbo.parqueadero.exception.ObjetoNoExisteException;
import com.nelumbo.parqueadero.repository.RolRepository;
import com.nelumbo.parqueadero.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Rol obtenerRolPorId(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(()-> new ObjetoNoExisteException("El rol con el ID: " + id + " no exite."));
    }
}
