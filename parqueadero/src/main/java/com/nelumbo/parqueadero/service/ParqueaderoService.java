package com.nelumbo.parqueadero.service;

import com.nelumbo.parqueadero.domain.Parqueadero;
import com.nelumbo.parqueadero.dto.request.ParqueaderoActualizarRequest;
import com.nelumbo.parqueadero.dto.request.ParqueaderoRequest;
import com.nelumbo.parqueadero.dto.response.ParqueaderoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParqueaderoService {

    ParqueaderoResponse agregarParqueadero(ParqueaderoRequest parqueaderoRequest);

    ParqueaderoResponse actualizarParqueadero(Long id, ParqueaderoActualizarRequest parqueaderoActualizarRequest);

    void eliminarParqueadero(Long id);

    List <ParqueaderoResponse> verParqueaderos();

    ParqueaderoResponse verParqueadero(Long id);

    Boolean parqueaderoValido(Long idSocio, Long idParqueadero);

    Parqueadero obtenerParqueadero(Long id);

    List <ParqueaderoResponse> verParqueaderosSocio(Long id);
}
