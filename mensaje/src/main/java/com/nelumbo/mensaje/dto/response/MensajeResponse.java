package com.nelumbo.mensaje.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MensajeResponse {

    private String email;
    private String placa;
    private String mensaje;
    private String parqueaderoNombre;
}
