package com.nelumbo.mensaje.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MensajeRequest {

    @NotBlank(message = "El correo no puede ser vacio")
    @NotNull(message = "El correo es requerido")
    @Email(message = "Ingrese un correo valido")
    private String email;
    @NotBlank(message = "La placa no puede ser vacia")
    @NotNull(message = "La placa es requeridoA")
    private String placa;
    @NotBlank(message = "El mensaje no puede ser vacio")
    @NotNull(message = "El mensaje es requerido")
    private String mensaje;
    @NotBlank(message = "El nombre del parqueadero no puede ser vacio")
    @NotNull(message = "El nombre del parqueadero es requerido")
    private String parqueaderoNombre;
}
