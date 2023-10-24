package com.nelumbo.parqueadero.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EntradaVehiculoRequest {

    @NotNull(message = "El id del parqueadero no puede ser nulo")
    @Min(value = 1, message = "Ingrese un numero de parqueadero valido")
    private Long idParqueadero;

    @Length(min = 6, max = 6)
    @NotBlank(message = "La placa es requerida")
    @NotNull(message = "La placa es requerida")
    @Pattern(regexp = "^[a-zA-Z0-9&&[^ñ]]*$", message = "No se permite caracteres especiales ni la letra ñ")
    private String placa;
}
