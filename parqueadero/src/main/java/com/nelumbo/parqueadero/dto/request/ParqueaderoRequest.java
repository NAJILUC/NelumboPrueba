package com.nelumbo.parqueadero.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParqueaderoRequest {

    @NotBlank(message = "El nombre es requerido")
    @NotNull(message = "El nombre es requerido")
    private String nombre;

    @NotNull(message = "No puede ser nulo")
    @Min(value = 1, message = "Debe ser minimo uno")
    @Max(value = 10000, message = "Supero el numero maximo de vehiculos permitido")
    private Long vehiculosMaximos;

    @NotNull(message = "No puede ser nulo")
    @Min(value = 1, message = "Debe ser minimo 1")
    @Max(value = Long.MAX_VALUE, message = "Supero el costo maximo permitido")
    private Double costo;

    @NotNull(message = "No puede ser nulo")
    @NotBlank(message = "El correo es requerido")
    @Email(message = "Ingrese un correo valido")
    private String correo;
}
