package com.nelumbo.parqueadero.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorResponseMessage.class)
    public ResponseEntity<String> errorResponseMessage(ErrorResponseMessage ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ObjetoDuplicadoException.class)
    public ResponseEntity<String> objetoDuplicado(ObjetoDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(VehiculoExisteException.class)
    public ResponseEntity<String> vehiculoExisteException(VehiculoExisteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

   // @ExceptionHandler(UsuarioDuplicadoException.class)
    public ResponseEntity<String> usuarioDuplicado(UsuarioDuplicadoException ex) {
        String errorMessage = "El usuario ya se encuentra registrado";
        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AdminAsociadoException.class)
    public ResponseEntity<String> adminAsociado(AdminAsociadoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
