package com.nelumbo.parqueadero.service.impl;

import com.nelumbo.parqueadero.domain.Parqueadero;
import com.nelumbo.parqueadero.domain.Usuario;
import com.nelumbo.parqueadero.dto.request.ParqueaderoActualizarRequest;
import com.nelumbo.parqueadero.dto.request.ParqueaderoRequest;
import com.nelumbo.parqueadero.dto.response.ParqueaderoResponse;
import com.nelumbo.parqueadero.exception.AdminAsociadoException;
import com.nelumbo.parqueadero.exception.NotFoundException;
import com.nelumbo.parqueadero.exception.ObjetoDuplicadoException;
import com.nelumbo.parqueadero.repository.ParqueaderoRepository;
import com.nelumbo.parqueadero.service.ParqueaderoService;
import com.nelumbo.parqueadero.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ParqueaderoServiceImpl implements ParqueaderoService {

    @Autowired
    ParqueaderoRepository parqueaderoRepository;

    @Autowired
    UsuarioService usuarioService;

    @Override
    public ParqueaderoResponse agregarParqueadero(ParqueaderoRequest parqueaderoRequest) {
        if(usuarioService.obtenerUsuarioPorCorreo(parqueaderoRequest.getCorreo()).getRol().getId()==1)
            throw new AdminAsociadoException("El usuario administrador no puede ser socio de un parqueadero");

        Usuario socio = usuarioService.obtenerUsuarioPorCorreo(parqueaderoRequest.getCorreo());
        if(socio==null) throw new NotFoundException("El socio con el correo " + parqueaderoRequest.getCorreo() + "no existe");

        Parqueadero parqueadero = Parqueadero.builder()
                .nombre(parqueaderoRequest.getNombre())
                .vehiculosMaximos(parqueaderoRequest.getVehiculosMaximos())
                .costo(parqueaderoRequest.getCosto())
                .fechaRegistro(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .usuario(socio)
                .build();

        try {
            Long id = parqueaderoRepository.save(parqueadero).getId();
            return new ParqueaderoResponse(id,parqueadero.getNombre(),parqueadero.getVehiculosMaximos(),
                    parqueadero.getCosto(),parqueadero.getUsuario() != null ?
                    parqueadero.getUsuario().getCorreo() : "Sin Socio asociado");
        } catch (DataIntegrityViolationException e) {
            throw new ObjetoDuplicadoException("El parqueadero " + parqueadero.getNombre() + " ya se encuentra registrado");
        }
    }

    @Override
    public ParqueaderoResponse actualizarParqueadero(Long id, ParqueaderoActualizarRequest parqueaderoActualizarRequest) {
        Parqueadero parqueadero = buscarParqueaderoPorId(id);

        if(usuarioService.obtenerUsuarioPorCorreo(parqueaderoActualizarRequest.getCorreo()).getRol().getId()==1)
            throw new AdminAsociadoException("El usuario administrador no puede ser socio de un parqueadero");

        if(parqueadero != null){
            parqueadero.setVehiculosMaximos(parqueaderoActualizarRequest.getVehiculosMaximos() != null ?
                    parqueaderoActualizarRequest.getVehiculosMaximos() : parqueadero.getVehiculosMaximos());
            parqueadero.setNombre(parqueaderoActualizarRequest.getNombre() != null ?
                    parqueaderoActualizarRequest.getNombre() : parqueadero.getNombre());
            parqueadero.setCosto(parqueaderoActualizarRequest.getCosto() != null ?
                    parqueaderoActualizarRequest.getCosto() : parqueadero.getCosto());
            if(usuarioService.obtenerUsuarioPorCorreo(parqueaderoActualizarRequest.getCorreo())!= null)
                parqueadero.setUsuario(usuarioService.obtenerUsuarioPorCorreo(parqueaderoActualizarRequest.getCorreo()));
            parqueaderoRepository.save(parqueadero);
        }
        return new ParqueaderoResponse(id,parqueadero.getNombre(),parqueadero.getVehiculosMaximos(),parqueadero.getCosto()
                ,parqueadero.getUsuario().getCorreo());
    }

    public Parqueadero buscarParqueaderoPorId(Long id){
        return parqueaderoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El Parqueadero con el id "+ id + " no existe"));
    }

    @Override
    public ParqueaderoResponse verParqueadero(Long id) {
        Parqueadero parqueadero = buscarParqueaderoPorId(id);
        return new ParqueaderoResponse(id,parqueadero.getNombre(),parqueadero.getVehiculosMaximos(),parqueadero.getCosto()
                ,parqueadero.getUsuario().getCorreo());
    }

    @Override
    public List<ParqueaderoResponse> verParqueaderos() {
        List<ParqueaderoResponse> parqueaderosResponses = new ArrayList<>();
        List<Parqueadero> parqueaderos = parqueaderoRepository.findAll();
        for (Parqueadero parqueadero:parqueaderos) {
            if(parqueadero.getUsuario()==null)
                parqueaderosResponses.add(new ParqueaderoResponse(parqueadero.getId(),parqueadero.getNombre(),parqueadero.getVehiculosMaximos(),parqueadero.getCosto()
                        ,"Sin socio asociado"));
            else parqueaderosResponses.add(new ParqueaderoResponse(parqueadero.getId(),parqueadero.getNombre(),parqueadero.getVehiculosMaximos(),parqueadero.getCosto()
                    ,parqueadero.getUsuario().getCorreo()));
        }
        return parqueaderosResponses;
    }

    @Override
    public void eliminarParqueadero(Long id) {
        buscarParqueaderoPorId(id);
        parqueaderoRepository.deleteById(id);
    }

    public Boolean parqueaderoValido(Long idSocio, Long idParqueadero){
        if(buscarParqueaderoPorId(idParqueadero).getUsuario().getId()==idSocio) return true;
        else return false;
    }

    @Override
    public Parqueadero obtenerParqueadero(Long id) {
        Optional<Parqueadero> parqueadero = parqueaderoRepository.findById(id);
        return parqueadero != null ? parqueadero.get() : null;
    }

    @Override
    public List<ParqueaderoResponse> verParqueaderosSocio(Long id) {
        List<Parqueadero> parqueaderos = parqueaderoRepository.findByUserId(id);
        if(parqueaderos.isEmpty())
            throw new NotFoundException("El Socio no tiene parqueaderos asociados o el Socio no existe");
        List<ParqueaderoResponse> parqueaderoResponses = new ArrayList<>();
        for (Parqueadero parqueadero : parqueaderos) {
            parqueaderoResponses.add(ParqueaderoResponse.builder()
                    .correoSocio(parqueadero.getUsuario().getCorreo())
                    .costo(parqueadero.getCosto())
                    .nombre(parqueadero.getNombre())
                    .vehiculosMaximos(parqueadero.getVehiculosMaximos())
                    .build());
        }
        return parqueaderoResponses;
    }
}
