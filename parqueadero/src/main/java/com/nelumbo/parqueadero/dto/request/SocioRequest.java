package com.nelumbo.parqueadero.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SocioRequest {

    @NotBlank(message = "El nombre no puede ser vacio")
    @NotNull(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "El correo no puede ser vacio")
    @NotNull(message = "El correo es requerido")
    private String correo;

    @NotBlank(message = "La contrasena no puede ser vacia")
    @NotNull(message = "La contrasena es requeridA")
    private String contrasena;
}
