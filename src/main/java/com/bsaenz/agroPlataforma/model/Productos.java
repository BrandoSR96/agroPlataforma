package com.bsaenz.agroPlataforma.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "productos")
public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer cantidadDisponible;
    private String distrito;
    private String categoria;
    private String imagenUrl;

    private Instant creadoEn;
    private Instant actualizadoEn;

}
