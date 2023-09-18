package com.nelumbo.parqueadero.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class ErrorResponseMessage extends RuntimeException{
    private String errorMessage;
}