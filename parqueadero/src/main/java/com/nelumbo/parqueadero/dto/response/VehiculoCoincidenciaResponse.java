package com.nelumbo.parqueadero.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehiculoCoincidenciaResponse {

    private Long idParqueadero;
    private Long id;
    private String placa;
    private Date horaIngreso;
}
