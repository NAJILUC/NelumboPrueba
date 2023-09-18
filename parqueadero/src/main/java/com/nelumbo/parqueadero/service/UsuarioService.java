package com.nelumbo.parqueadero.service;

import com.nelumbo.parqueadero.domain.Usuario;
import com.nelumbo.parqueadero.dto.request.SocioRequest;
import com.nelumbo.parqueadero.dto.response.SocioResponse;
import org.springframework.stereotype.Service;

@Service
public interface UsuarioService {
    SocioResponse guardarSocio(SocioRequest socioRequest);

    Usuario obtenerUsuarioPorCorreo(String correo);
}
