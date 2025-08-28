package com.bsaenz.agroPlataforma.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class ProductosDto {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 60, message = "El nombre debe tener entre 3 y 60 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", inclusive = true, message = "El precio debe ser mayor a 0.01")
    private Double precio;

    @NotNull(message = "La cantidad disponible es obligatoria")
    @Min(value = 1, message = "La cantidad disponible debe ser al menos 1")
    private Integer cantidadDisponible;

    @NotBlank(message = "El distrito es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóú ]{3,30}$", message = "El distrito debe tener entre 3 y 30 letras y espacios")
    private String distrito;

    private String categoria;

    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "La URL de la imagen debe ser válida")
    private String imagenUrl;

    private String creadoEn;
    private String actualizadoEn;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.systemDefault());
    public ProductosDto(Long id, String nombre, String descripcion, Double precio, Integer cantidadDisponible, String distrito, String categoria, String imagenUrl, Instant creadoEn, Instant actualizadoEn) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
        this.distrito = distrito;
        this.categoria = categoria;
        this.imagenUrl = imagenUrl;
        this.creadoEn = creadoEn != null ? formatter.format(creadoEn) : null;
        this.actualizadoEn = actualizadoEn != null ? formatter.format(actualizadoEn) : null;
    }
}
