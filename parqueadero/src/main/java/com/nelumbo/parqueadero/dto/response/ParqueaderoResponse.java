package com.nelumbo.parqueadero.dto.response;

import com.nelumbo.parqueadero.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ParqueaderoResponse {

    private String nombre;
    private Long vehiculosMaximos;
    private double costo;
    private String correoSocio;
}
