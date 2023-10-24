package com.nelumbo.mensaje.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "my_collection")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Historico {
    @Id
    private String id;
    private String email;
    private String placa;
    private String mensaje;
    private String nombre;
    private Date fechaRegistro;
}