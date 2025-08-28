package com.bsaenz.agroPlataforma.dto;

import jakarta.ws.rs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import java.io.InputStream;

public class ProductoForm {

    @FormParam("nombre")
    @PartType("text/plain")
    public String nombre;

    @FormParam("descripcion")
    @PartType("text/plain")
    public String descripcion;

    @FormParam("precio")
    @PartType("text/plain")
    public Double precio;

    @FormParam("cantidadDisponible")
    @PartType("text/plain")
    public Integer cantidadDisponible;

    @FormParam("distrito")
    @PartType("text/plain")
    public String distrito;

    @FormParam("categoria")
    @PartType("text/plain")
    public String categoria;

    @FormParam("imagen")
    @PartType("application/octet-stream")
    public InputStream imagen;
}
