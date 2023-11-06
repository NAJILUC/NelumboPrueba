package com.nelumbo.parqueadero.controller;

import com.nelumbo.parqueadero.dto.response.IndicadorVehiculoResponse;
import com.nelumbo.parqueadero.dto.response.VehiculoCoincidenciaResponse;
import com.nelumbo.parqueadero.dto.response.VehiculoResponse;
import com.nelumbo.parqueadero.service.HistoricoService;
import com.nelumbo.parqueadero.service.ParqueaderoVehiculoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/indicadores")
@AllArgsConstructor
public class IndicadoresController {

    @Autowired
    ParqueaderoVehiculoService parqueaderoVehiculoService;

    @Autowired
    HistoricoService historicoService;

    @GetMapping("/frecuentes")
    @PreAuthorize("hasAuthority('SOCIO') OR hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<IndicadorVehiculoResponse> vehiculosFrecuentes(){
        return parqueaderoVehiculoService.vehiculosFrecuentes();
    }

    @GetMapping("/frecuentes/{id_Parqueadero}")
    @PreAuthorize("hasAuthority('SOCIO') OR hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<IndicadorVehiculoResponse> vehiculosFrecuentes(@PathVariable(name = "id_Parqueadero") Long id_Parqueadero){
        return parqueaderoVehiculoService.vehiculosFrecuentes(id_Parqueadero);
    }

    @GetMapping("/vehiculosnuevos/{id_Parqueadero}")
    @PreAuthorize("hasAuthority('SOCIO') OR hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<VehiculoResponse> vehiculosNuevos(@PathVariable(name = "id_Parqueadero") Long id_Parqueadero){
        return parqueaderoVehiculoService.vehiculosNuevos(id_Parqueadero);
    }

    @GetMapping("/coincidencia/{coincidencia}")
    @PreAuthorize("hasAuthority('SOCIO') OR hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<VehiculoCoincidenciaResponse> vehiculosCoinciden(@PathVariable(name = "coincidencia") String coincidencia){
        return parqueaderoVehiculoService.vehiculosCoindicencia(coincidencia);
    }

    @GetMapping("/gananciasdia/{id_Parqueadero}")
    @PreAuthorize("hasAuthority('SOCIO')")
    @ResponseStatus(HttpStatus.OK)
    public Double ganancaisDia(@PathVariable(name = "id_Parqueadero") Long id_Parqueadero){
        return historicoService.gananciasHoy(id_Parqueadero);
    }

    @GetMapping("/gananciasmes/{id_Parqueadero}")
    @PreAuthorize("hasAuthority('SOCIO')")
    @ResponseStatus(HttpStatus.OK)
    public Double ganancaisMes(@PathVariable(name = "id_Parqueadero") Long id_Parqueadero){
        return historicoService.gananciasMes(id_Parqueadero);
    }

    @GetMapping("/gananciasanio/{id_Parqueadero}")
    @PreAuthorize("hasAuthority('SOCIO')")
    @ResponseStatus(HttpStatus.OK)
    public Double ganancaisanio(@PathVariable(name = "id_Parqueadero") Long id_Parqueadero){
        return historicoService.gananciasAnio(id_Parqueadero);
    }
}
