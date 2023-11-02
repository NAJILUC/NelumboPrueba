package com.nelumbo.parqueadero.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorResponseMessage.class)
    public ResponseEntity<CustomErrorResponse> errorResponseMessage(ErrorResponseMessage ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomErrorResponse> notFoundException(NotFoundException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ObjetoDuplicadoException.class)
    public ResponseEntity<CustomErrorResponse> objetoDuplicado(ObjetoDuplicadoException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(VehiculoExisteException.class)
    public ResponseEntity<CustomErrorResponse> vehiculoExisteException(VehiculoExisteException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

   @ExceptionHandler(UsuarioDuplicadoException.class)
   public ResponseEntity<CustomErrorResponse> handleUsuarioDuplicadoException(UsuarioDuplicadoException ex) {
       CustomErrorResponse errorResponse = new CustomErrorResponse(
               HttpStatus.BAD_REQUEST.value(),
               HttpStatus.BAD_REQUEST.toString(),
               ex.getMessage()
       );
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
   }

    @ExceptionHandler(CampoInsuficienteException.class)
    public ResponseEntity<CustomErrorResponse> handleCampoInsuficiente(CampoInsuficienteException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AdminAsociadoException.class)
    public ResponseEntity<CustomErrorResponse> adminAsociado(AdminAsociadoException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
