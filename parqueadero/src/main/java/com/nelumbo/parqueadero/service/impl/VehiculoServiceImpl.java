package com.nelumbo.parqueadero.service.impl;

import com.nelumbo.parqueadero.domain.ParqueaderoVehiculo;
import com.nelumbo.parqueadero.domain.Vehiculo;
import com.nelumbo.parqueadero.exception.VehiculoExisteException;
import com.nelumbo.parqueadero.repository.ParqueaderoVehiculoRepository;
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

    @Autowired
    private ParqueaderoVehiculoRepository parqueaderoVehiculoRepository;

    @Override
    public Vehiculo agregarVehiculo(String placa) {
        Vehiculo vehiculo = obtenerVehiculo(placa);
        if(vehiculo==null){
            Vehiculo vehiculoNew = Vehiculo.builder()
                    .placa(placa.toUpperCase())
                    .build();
            try {
                return vehiculoRepository.save(vehiculoNew);
            } catch (DataIntegrityViolationException ignored) {
            }
        }
        return vehiculo;
    }
    @Override
    public Boolean existeVehiculoEntrada(String placa) {
        Vehiculo vehiculo = obtenerVehiculo(placa);
        if(vehiculo==null){
            return false;
        }
        Optional<ParqueaderoVehiculo> existe = parqueaderoVehiculoRepository.findByVehiculoId(vehiculo.getId());
        if(!existe.isEmpty()) throw new VehiculoExisteException("El vehiculo ya existe en algun parquedero");
        return existe != null ? true : false;
    }

    @Override
    public Boolean existeVehiculo(String placa) {
        Vehiculo vehiculo = obtenerVehiculo(placa);
        if(vehiculo==null){
            return false;
        }
        Optional<ParqueaderoVehiculo> existe = parqueaderoVehiculoRepository.findByVehiculoId(vehiculo.getId());
        return existe.isPresent();
    }

    @Override
    public Vehiculo obtenerVehiculo(String placa) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findByPlaca(placa.toUpperCase());
        return vehiculo.orElse(null);
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
