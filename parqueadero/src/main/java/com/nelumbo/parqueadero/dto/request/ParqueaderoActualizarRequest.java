package com.nelumbo.parqueadero.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParqueaderoActualizarRequest {

    private String nombre;

    @Min(value = 1, message = "Debe ser minimo uno")
    private Long vehiculosMaximos;

    @Min(value = 1, message = "Debe ser minimo 1")
    private Double costo;

    @Email(message = "Ingrese un correo valido")
    private String correo;
}
