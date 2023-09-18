package com.nelumbo.parqueadero.dto.response;

import com.nelumbo.parqueadero.domain.Vehiculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IndicadorVehiculoResponse {
    private String vehiculo;
    private Long visitas;
}
