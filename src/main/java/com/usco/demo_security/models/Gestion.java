package com.usco.demo_security.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Data
public class Gestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;

    private String ubicacion;

    @CreationTimestamp
    @Column(name = "hora_entrada", updatable = false)
    private Timestamp horaEntrada;

    @UpdateTimestamp
    @Column(name = "hora_salida")
    private Timestamp horaSalida;

    @ManyToOne(fetch = FetchType.LAZY)  // Relación con Marca
    @JoinColumn(name = "id_tipo_vehiculo", nullable = false)
    private TipoVehiculo tipoVehiculo;
}
