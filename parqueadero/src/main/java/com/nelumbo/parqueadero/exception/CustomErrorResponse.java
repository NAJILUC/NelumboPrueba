package com.nelumbo.parqueadero.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomErrorResponse {

    private int status;
    private String error;
    private String message;
    
}