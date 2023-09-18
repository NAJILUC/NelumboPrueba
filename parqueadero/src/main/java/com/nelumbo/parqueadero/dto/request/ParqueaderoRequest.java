package com.nelumbo.parqueadero.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParqueaderoRequest {

    @NotBlank(message = "El nombre es requerido")
    @NotNull(message = "El nombre es requerido")
    private String nombre;

    @NotNull(message = "No puede ser nulo")
    @Min(value = 1, message = "Debe ser minimo uno")
    private Long vehiculosMaximos;

    @NotNull(message = "No puede ser nulo")
    @Min(value = 1, message = "Debe ser minimo 1")
    private Double costo;

    @Email(message = "Ingrese un correo valido")
    private String correo;
}
