package com.nelumbo.parqueadero.feignClient.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MensajeRequest {

    @NotBlank(message = "El correo no puede ser vacio")
    @NotNull(message = "El correo es requerido")
    @Email(message = "Ingrese un correo valido")
    private String email;
    @Length(min = 6, max = 6)
    @NotBlank(message = "La placa es requerida")
    @NotNull(message = "La placa es requerida")
    @Pattern(regexp = "^[a-zA-Z0-9&&[^ñ]]*$", message = "No se permite caracteres especiales ni la letra ñ")
    private String placa;
    @NotBlank(message = "El mensaje no puede ser vacio")
    @NotNull(message = "El mensaje es requerido")
    private String mensaje;
    @NotBlank(message = "El nombre del parqueadero no puede ser vacio")
    @NotNull(message = "El nombre del parqueadero es requerido")
    private String parqueaderoNombre;
}
