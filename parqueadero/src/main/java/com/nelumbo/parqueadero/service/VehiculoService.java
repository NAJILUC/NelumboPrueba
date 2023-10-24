package com.nelumbo.parqueadero.service;

import com.nelumbo.parqueadero.domain.Vehiculo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehiculoService {
    String agregarVehiculo(String placa);

    Boolean existeVehiculo(String placa);
    Boolean existeVehiculoEntrada(String placa);

    Vehiculo obtenerVehiculo(String placa);

    void eliminarVehiculo(Long id);

    List<Vehiculo> coincidenciaDePlaca(String cadena);
}
