package com.nelumbo.parqueadero.service.impl;

import com.nelumbo.parqueadero.domain.Rol;
import com.nelumbo.parqueadero.domain.Usuario;
import com.nelumbo.parqueadero.repository.UsuarioRepository;
import com.nelumbo.parqueadero.service.UsuarioService;
import org.aspectj.weaver.patterns.IToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UsuarioServiceImplTest {

    @InjectMocks
    UsuarioServiceImpl usuarioService;

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    IToken iToken;

    @Test
    void mustGuardarSocio() {

        //Given
        Usuario expectedUser = Usuario.builder()
                .id(1L)
                .nombre("Prueba")
                .correo("prueba@gmail.com")
                .fechaRegistro(Date.from(Instant.now()))
                .contrasena("1234")
                .rol(Rol.builder()
                        .id(2L)
                        .nombre("SOCIO")
                        .build())
                .build();

        Rol rol = Rol.builder()
                .id(2L)
                .nombre("SOCIO")
                .build();
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Prueba")
                .correo("prueba@gmail.com")
                .fechaRegistro(Date.from(Instant.now()))
                .contrasena("1234")
                .rol(rol)
                .build();

        //When
        usuarioRepository.save(usuario);

        //Then
        assertEquals(expectedUser.getContrasena(),usuario.getContrasena());
        assertEquals(expectedUser.getCorreo(),usuario.getCorreo());
        assertEquals(expectedUser.getNombre(),usuario.getNombre());
        assertEquals(expectedUser.getId(),usuario.getId());
        assertEquals(expectedUser.getRol(),usuario.getRol());
    }

    @Test
    void mustObtenerUsuarioPorCorreo() {

        //Given
        Usuario expectedUser = Usuario.builder()
                .id(1L)
                .nombre("Prueba")
                .correo("prueba@gmail.com")
                .fechaRegistro(Date.from(Instant.now()))
                .contrasena("1234")
                .rol(Rol.builder()
                        .id(2L)
                        .nombre("SOCIO")
                        .build())
                .build();

        Rol rol = Rol.builder()
                .id(2L)
                .nombre("SOCIO")
                .build();
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Prueba")
                .correo("prueba@gmail.com")
                .fechaRegistro(Date.from(Instant.now()))
                .contrasena("1234")
                .rol(rol)
                .build();

        //When
        Mockito.when(usuarioRepository.findByCorreo(Mockito.anyString())).thenReturn(usuario);
        Usuario usuarioRespuesta = usuarioRepository.findByCorreo(expectedUser.getCorreo());

        //Then
        assertEquals(expectedUser.getContrasena(),usuarioRespuesta.getContrasena());
        assertEquals(expectedUser.getCorreo(),usuarioRespuesta.getCorreo());
        assertEquals(expectedUser.getNombre(),usuarioRespuesta.getNombre());
        assertEquals(expectedUser.getId(),usuarioRespuesta.getId());
        assertEquals(expectedUser.getRol(),usuarioRespuesta.getRol());
    }
}