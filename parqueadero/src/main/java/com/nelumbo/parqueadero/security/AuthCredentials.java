package com.nelumbo.parqueadero.security;

import lombok.Data;

@Data
public class AuthCredentials {

    private String correo;
    private String clave;
}
