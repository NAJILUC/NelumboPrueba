package com.nelumbo.parqueadero.service.impl;

import com.nelumbo.parqueadero.domain.Usuario;
import com.nelumbo.parqueadero.dto.request.SocioRequest;
import com.nelumbo.parqueadero.dto.response.SocioResponse;
import com.nelumbo.parqueadero.exception.ObjetoDuplicadoException;
import com.nelumbo.parqueadero.repository.UsuarioRepository;
import com.nelumbo.parqueadero.service.RolService;
import com.nelumbo.parqueadero.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolService rolService;

    @Override
    public SocioResponse guardarSocio(SocioRequest socioRequest) {
        Usuario socio = Usuario.builder()
                .nombre(socioRequest.getNombre())
                .contrasena(passwordEncoder.encode(socioRequest.getContrasena()))
                .correo(socioRequest.getCorreo())
                .fechaRegistro(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .rol(rolService.obtenerRolPorId(2L))
                .build();

        try{
            usuarioRepository.save(socio);
            return new SocioResponse(socioRequest.getNombre(),socioRequest.getCorreo());
        } catch (DataIntegrityViolationException e) {
            throw new ObjetoDuplicadoException("El usuario " + socioRequest.getNombre() + " ya se encuentra registrado");
        }
    }

    @Override
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
}
