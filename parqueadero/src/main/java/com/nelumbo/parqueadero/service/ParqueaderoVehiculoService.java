package com.nelumbo.parqueadero.service;

import com.nelumbo.parqueadero.dto.request.EntradaVehiculoRequest;
import com.nelumbo.parqueadero.dto.request.SalidaRequest;
import com.nelumbo.parqueadero.dto.response.IndicadorVehiculoResponse;
import com.nelumbo.parqueadero.dto.response.VehiculoCoincidenciaResponse;
import com.nelumbo.parqueadero.dto.response.VehiculoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParqueaderoVehiculoService {

    List<VehiculoResponse> vehiculosEnParqueadero(Long id);

    Long entradaVehiculo(EntradaVehiculoRequest entradaVehiculoRequest);

    void salidaVehiculo(SalidaRequest salidaRequest);

    List<IndicadorVehiculoResponse> vehiculosFrecuentes();

    List<IndicadorVehiculoResponse> vehiculosFrecuentes(Long idParqueadero);

    List<VehiculoResponse> vehiculosNuevos(Long idParqueadero);

    List<VehiculoCoincidenciaResponse> vehiculosCoindicencia(String cadena);

}
