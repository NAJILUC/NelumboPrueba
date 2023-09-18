package com.nelumbo.parqueadero.dto.response;

import com.nelumbo.parqueadero.domain.Vehiculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehiculoResponse {

    private Long id;
    private String placa;
    private Date horaIngreso;
}
