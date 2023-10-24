package com.nelumbo.parqueadero.service.impl;

import com.nelumbo.parqueadero.domain.Vehiculo;
import com.nelumbo.parqueadero.exception.ObjetoDuplicadoException;
import com.nelumbo.parqueadero.exception.VehiculoExisteException;
import com.nelumbo.parqueadero.repository.VehiculoRepository;
import com.nelumbo.parqueadero.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public String agregarVehiculo(String placa) {
        Vehiculo vehiculo = Vehiculo.builder()
                .placa(placa.toUpperCase())
                .build();
        try {
            vehiculoRepository.save(vehiculo);
            return placa;
        } catch (DataIntegrityViolationException e) {
            throw new ObjetoDuplicadoException("El vehiculo con placa " + placa + " ya se encuentra registrado");
        }
    }
    @Override
    public Boolean existeVehiculoEntrada(String placa) {
        Optional<Vehiculo> existe = vehiculoRepository.findByPlaca(placa.toUpperCase());
        if(!existe.isEmpty()) throw new VehiculoExisteException("El vehiculo ya existe en algun parquedero");
        return existe != null ? true : false;
    }

    @Override
    public Boolean existeVehiculo(String placa) {
        Optional<Vehiculo> existe = vehiculoRepository.findByPlaca(placa.toUpperCase());
        if(existe.isEmpty()) throw new VehiculoExisteException("El vehiculo no existe en ningun parquedero");
        return existe != null ? true : false;
    }

    @Override
    public Vehiculo obtenerVehiculo(String placa) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findByPlaca(placa.toUpperCase());
        return vehiculo != null ? vehiculo.get() : null;
    }

    @Override
    public void eliminarVehiculo(Long id) {
        vehiculoRepository.deleteById(id);
    }

    @Override
    public List<Vehiculo> coincidenciaDePlaca(String cadena) {
        return vehiculoRepository.findByPlacaContaining(cadena.toUpperCase());
    }

}
