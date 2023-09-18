package com.nelumbo.parqueadero.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SocioResponse {
    private String nombre;
    private String correo;
}
