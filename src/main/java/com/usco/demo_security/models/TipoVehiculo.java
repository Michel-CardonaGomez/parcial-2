package com.usco.demo_security.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tipo_vehiculo")
@Data
public class TipoVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
}
