package com.nelumbo.parqueadero.exception;

public class VehiculoExisteException extends RuntimeException{
    public VehiculoExisteException(String menssage) {
        super(menssage);
    }
}
