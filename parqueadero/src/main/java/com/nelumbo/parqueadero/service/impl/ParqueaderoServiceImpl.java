package com.nelumbo.parqueadero.service.impl;

import com.nelumbo.parqueadero.domain.Parqueadero;
import com.nelumbo.parqueadero.domain.Usuario;
import com.nelumbo.parqueadero.dto.request.ParqueaderoActualizarRequest;
import com.nelumbo.parqueadero.dto.request.ParqueaderoRequest;
import com.nelumbo.parqueadero.dto.response.ParqueaderoResponse;
import com.nelumbo.parqueadero.exception.AdminAsociadoException;
import com.nelumbo.parqueadero.exception.ObjetoDuplicadoException;
import com.nelumbo.parqueadero.exception.ObjetoNoExisteException;
import com.nelumbo.parqueadero.repository.ParqueaderoRepository;
import com.nelumbo.parqueadero.service.ParqueaderoService;
import com.nelumbo.parqueadero.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParqueaderoServiceImpl implements ParqueaderoService {

    @Autowired
    ParqueaderoRepository parqueaderoRepository;

    @Autowired
    UsuarioService usuarioService;

    @Override
    public ParqueaderoResponse agregarParqueadero(ParqueaderoRequest parqueaderoRequest) {
        Usuario socio = usuarioService.obtenerUsuarioPorCorreo(parqueaderoRequest.getCorreo());
        if (socio == null)
            throw new ObjetoNoExisteException("El socio con el correo " + parqueaderoRequest.getCorreo() + " no existe");
        if (socio.getRol().getId() == 1)
            throw new AdminAsociadoException("El usuario administrador no puede ser socio de un parqueadero");
        Parqueadero parqueadero = Parqueadero.builder()
                .nombre(parqueaderoRequest.getNombre())
                .vehiculosMaximos(parqueaderoRequest.getVehiculosMaximos())
                .costo(parqueaderoRequest.getCosto())
                .fechaRegistro(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .usuario(socio)
                .build();
        try {
            Long id = parqueaderoRepository.save(parqueadero).getId();
            return ParqueaderoResponse.builder()
                    .id(id)
                    .nombre(parqueadero.getNombre())
                    .vehiculosMaximos(parqueadero.getVehiculosMaximos())
                    .costo(parqueadero.getCosto())
                    .correoSocio(parqueadero.getUsuario().getCorreo())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ObjetoDuplicadoException("El parqueadero " + parqueadero.getNombre() + " ya se encuentra registrado");
        }
    }

    @Override
    public ParqueaderoResponse actualizarParqueadero(Long id, ParqueaderoActualizarRequest parqueaderoActualizarRequest) {
        Parqueadero parqueadero = buscarParqueaderoPorId(id);
        Usuario user = null;
        if (parqueaderoActualizarRequest.getCorreo() != null) {
            try {
                user = usuarioService.obtenerUsuarioPorCorreo(parqueaderoActualizarRequest.getCorreo());
                if (user.getRol().getId() == 1)
                    throw new AdminAsociadoException("El usuario administrador no puede ser socio de un parqueadero");
            } catch (Exception e) {
                throw new ObjetoNoExisteException("El correo " + parqueaderoActualizarRequest.getCorreo() + " No esta asociado a ningun socio");
            }
        }
        parqueadero.setVehiculosMaximos(parqueaderoActualizarRequest.getVehiculosMaximos() != null ?
                parqueaderoActualizarRequest.getVehiculosMaximos() : parqueadero.getVehiculosMaximos());
        parqueadero.setNombre(parqueaderoActualizarRequest.getNombre() != null ?
                parqueaderoActualizarRequest.getNombre() : parqueadero.getNombre());
        parqueadero.setCosto(parqueaderoActualizarRequest.getCosto() != null ?
                parqueaderoActualizarRequest.getCosto() : parqueadero.getCosto());
        if (usuarioService.obtenerUsuarioPorCorreo(parqueaderoActualizarRequest.getCorreo()) != null)
            parqueadero.setUsuario(user);
        parqueaderoRepository.save(parqueadero);
        return new ParqueaderoResponse(id, parqueadero.getNombre(), parqueadero.getVehiculosMaximos(), parqueadero.getCosto()
                , parqueadero.getUsuario().getCorreo());
    }

    public Parqueadero buscarParqueaderoPorId(Long id) {
        return parqueaderoRepository.findById(id)
                .orElseThrow(() -> new ObjetoNoExisteException("El Parqueadero con el id " + id + " no existe"));
    }

    @Override
    public ParqueaderoResponse verParqueadero(Long id) {
        Parqueadero parqueadero = buscarParqueaderoPorId(id);
        return ParqueaderoResponse.builder()
                .id(parqueadero.getId())
                .nombre(parqueadero.getNombre())
                .vehiculosMaximos(parqueadero.getVehiculosMaximos())
                .costo(parqueadero.getCosto())
                .correoSocio(parqueadero.getUsuario().getCorreo())
                .build();
    }

    @Override
    public List<ParqueaderoResponse> verParqueaderos() {
        return parqueaderoRepository.findAll()
                .stream()
                .map(parqueadero -> ParqueaderoResponse.builder()
                        .id(parqueadero.getId())
                        .nombre(parqueadero.getNombre())
                        .vehiculosMaximos(parqueadero.getVehiculosMaximos())
                        .costo(parqueadero.getCosto())
                        .correoSocio(parqueadero.getUsuario().getCorreo())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarParqueadero(Long id) {
        buscarParqueaderoPorId(id);
        parqueaderoRepository.deleteById(id);
    }

    public Boolean parqueaderoValido(Long idSocio, Long idParqueadero) {
        return buscarParqueaderoPorId(idParqueadero).getUsuario().getId() == idSocio;
    }

    @Override
    public Parqueadero obtenerParqueadero(Long id) {
        return parqueaderoRepository.findById(id)
                .orElseThrow(() -> new ObjetoNoExisteException("El Parqueadero con el id " + id + " no existe"));
    }

    @Override
    public List<ParqueaderoResponse> verParqueaderosSocio(Long id) {
        List<Parqueadero> parqueaderos = parqueaderoRepository.findByUserId(id);
        if (parqueaderos.isEmpty())
            throw new ObjetoNoExisteException("El Socio no tiene parqueaderos asociados o el Socio no existe");
        return parqueaderos
                .stream()
                .map(parqueadero -> ParqueaderoResponse.builder()
                        .id(parqueadero.getId())
                        .nombre(parqueadero.getNombre())
                        .vehiculosMaximos(parqueadero.getVehiculosMaximos())
                        .costo(parqueadero.getCosto())
                        .correoSocio(parqueadero.getUsuario().getCorreo())
                        .build())
                .collect(Collectors.toList());
    }
}