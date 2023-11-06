package com.nelumbo.parqueadero.controller;

import com.nelumbo.parqueadero.dto.request.ParqueaderoActualizarRequest;
import com.nelumbo.parqueadero.dto.request.ParqueaderoRequest;
import com.nelumbo.parqueadero.dto.response.ParqueaderoResponse;
import com.nelumbo.parqueadero.dto.response.SalidaResponse;
import com.nelumbo.parqueadero.dto.response.VehiculoResponse;
import com.nelumbo.parqueadero.feignClient.MicroservicioMensajeService;
import com.nelumbo.parqueadero.feignClient.dto.request.MensajeRequest;
import com.nelumbo.parqueadero.feignClient.dto.response.MensajeResponse;
import com.nelumbo.parqueadero.service.ParqueaderoService;
import com.nelumbo.parqueadero.service.ParqueaderoVehiculoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/parqueaderoadmin")
@AllArgsConstructor
public class ParqueaderoAdminController {

    @Autowired
    private ParqueaderoService parqueaderoService;

    @Autowired
    private ParqueaderoVehiculoService parqueaderoVehiculoService;

    @Autowired
    private MicroservicioMensajeService microservicioMensajeService;

    @PostMapping("/parqueaderos")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ParqueaderoResponse registrarParqueadero(@Valid @RequestBody ParqueaderoRequest parqueaderoRequest){
        return parqueaderoService.agregarParqueadero(parqueaderoRequest);
    }

    @PutMapping("/parqueaderos/{id_Parqueadero}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ParqueaderoResponse actualizarParqueadero(@PathVariable(name = "id_Parqueadero") Long id_Parqueadero,
                                                     @Valid @RequestBody ParqueaderoActualizarRequest parqueaderoActualizarRequest){
        return parqueaderoService.actualizarParqueadero(id_Parqueadero,parqueaderoActualizarRequest);
    }

    @GetMapping("/parqueaderos/{id_Parqueadero}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ParqueaderoResponse verParqueadero(@PathVariable(name = "id_Parqueadero") Long id_Parqueadero){
        return parqueaderoService.verParqueadero(id_Parqueadero);
    }

    @GetMapping("/parqueaderos")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<ParqueaderoResponse> verParqueaderos(){
        return parqueaderoService.verParqueaderos();
    }

    @DeleteMapping("/parqueaderos/{id_Parqueadero}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String eliminarParqueadero(@PathVariable(name = "id_Parqueadero") Long id_Parqueadero){
        parqueaderoService.eliminarParqueadero(id_Parqueadero);
        return "El parqueadero con el id = " + id_Parqueadero + " fue eliminado correctamente";
    }

    @PutMapping("/asociarsocio/{id_Parqueadero}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ParqueaderoResponse asociarSocio(@PathVariable(name = "id_Parqueadero") Long id_Parqueadero,
                                                     @Valid @RequestBody ParqueaderoActualizarRequest parqueaderoActualizarRequest){
        return parqueaderoService.actualizarParqueadero(id_Parqueadero,parqueaderoActualizarRequest);
    }

    @GetMapping("/vehiculosenparqueadero/{id_Parqueadero}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<VehiculoResponse> vehiculosEnParqueadero(@PathVariable(name = "id_Parqueadero") Long id_Parqueadero){
        return parqueaderoVehiculoService.vehiculosEnParqueadero(id_Parqueadero);
    }

    @PostMapping("/enviarmensaje")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public SalidaResponse enviarMensaje(@Valid @RequestBody MensajeRequest mensajeRequest){
        try {
            MensajeResponse response = microservicioMensajeService.enviarMensaje(mensajeRequest);
        } catch (Exception ignored){}
        return SalidaResponse.builder().mensaje("Correo Enviado").build();
    }
}
