package com.nelumbo.parqueadero.service;

import com.nelumbo.parqueadero.domain.Vehiculo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehiculoService {
    Vehiculo agregarVehiculo(String placa);
    Boolean existeVehiculo(String placa);
    Vehiculo obtenerVehiculo(String placa);
    Vehiculo obtenerVehiculo(Long id);
    void eliminarVehiculo(Long id);
    List<Vehiculo> coincidenciaDePlaca(String cadena);
}
