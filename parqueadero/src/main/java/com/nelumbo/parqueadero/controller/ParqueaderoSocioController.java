package com.nelumbo.parqueadero.controller;

import com.nelumbo.parqueadero.dto.request.EntradaVehiculoRequest;
import com.nelumbo.parqueadero.dto.request.SalidaRequest;
import com.nelumbo.parqueadero.dto.response.IndicadorVehiculoResponse;
import com.nelumbo.parqueadero.dto.response.IngresoResponse;
import com.nelumbo.parqueadero.dto.response.ParqueaderoResponse;
import com.nelumbo.parqueadero.dto.response.SalidaResponse;
import com.nelumbo.parqueadero.dto.response.VehiculoResponse;
import com.nelumbo.parqueadero.service.ParqueaderoService;
import com.nelumbo.parqueadero.service.ParqueaderoVehiculoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/parqueaderosocio")
@AllArgsConstructor
public class ParqueaderoSocioController {

    @Autowired
    private ParqueaderoService parqueaderoService;

    @Autowired
    private ParqueaderoVehiculoService parqueaderoVehiculoService;


    @PostMapping("/ingresovehiculo")
    @PreAuthorize("hasAuthority('SOCIO')")
    @ResponseStatus(HttpStatus.CREATED)
    public IngresoResponse ingresoVehiculo(@Valid @RequestBody EntradaVehiculoRequest entradaVehiculoRequest){
        return new IngresoResponse(parqueaderoVehiculoService.entradaVehiculo(entradaVehiculoRequest));
    }

    @PostMapping("/salidavehiculo")
    @PreAuthorize("hasAuthority('SOCIO')")
    @ResponseStatus(HttpStatus.OK)
    public SalidaResponse salidaVehiculo(@Valid @RequestBody SalidaRequest salidaRequest){
        parqueaderoVehiculoService.salidaVehiculo(salidaRequest);
        return new SalidaResponse("Salida registrada");
    }

    @GetMapping("/parqueaderosasignados/{id_Socio}")
    @PreAuthorize("hasAuthority('SOCIO')")
    @ResponseStatus(HttpStatus.OK)
    public List<ParqueaderoResponse> parqueaderos(@PathVariable(name = "id_Socio") Long id_Socio){
        return parqueaderoService.verParqueaderosSocio(id_Socio);
    }

    @GetMapping("/vehiculosenparqueadero/{id_Parqueadero}")
    @PreAuthorize("hasAuthority('SOCIO')")
    @ResponseStatus(HttpStatus.OK)
    public List<VehiculoResponse> vehiculosEnParqueadero(@PathVariable(name = "id_Parqueadero") Long id_Parqueadero){
        return parqueaderoVehiculoService.vehiculosEnParqueadero(id_Parqueadero);
    }
}
