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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "parqueadero_vehiculo")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ParqueaderoVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "hora_ingreso")
    private Date horaIngreso;

    @ManyToOne
    @JoinColumn(name = "vehiculo.id")
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "parqueadero.id")
    private Parqueadero parqueadero;
}
