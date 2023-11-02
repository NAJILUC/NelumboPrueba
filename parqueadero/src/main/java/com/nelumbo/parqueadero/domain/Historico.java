package com.nelumbo.parqueadero.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "historico")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placa")
    private String placa;

    @Column(name = "id_parqueadero")
    private Long idParqueadero;

    @Column(name = "nombre_parqueadero")
    private String nombreParqueadero;

    @Column(name = "pago")
    private Double pago;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "hora_entrada")
    private Date horaEnrada;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "hora_salida")
    private Date horaSalida;
}
