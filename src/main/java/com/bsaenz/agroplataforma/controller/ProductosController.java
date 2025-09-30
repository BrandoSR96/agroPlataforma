package com.bsaenz.agroplataforma.controller;

import com.bsaenz.agroplataforma.dto.ProductoForm;
import com.bsaenz.agroplataforma.dto.ProductosDto;
import com.bsaenz.agroplataforma.model.Productos;
import com.bsaenz.agroplataforma.service.CloudinaryService;
import com.bsaenz.agroplataforma.service.ProductosService;
import com.bsaenz.agroplataforma.util.MensajeProducto;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/api/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductosController {

    @Inject
    ProductosService productosService;

    @Inject
    CloudinaryService cloudinaryService;

    @GET
    @Path("/listar")
    public Response obtenerProductos() {
        List<ProductosDto> productosDto = productosService.getRepository().listAll()
                .stream()
                .map(producto -> new ProductosDto(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getPrecio(),
                        producto.getCantidadDisponible(),
                        producto.getDistrito(),
                        producto.getCategoria(),
                        producto.getImagenUrl(),
                        producto.getCreadoEn(),
                        producto.getActualizadoEn()
                ))
                .collect(Collectors.toList());

        return Response.ok(productosDto).build();
    }


    @POST
    @Path("/crear")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response crearProducto(@MultipartForm ProductoForm form) {
        if (form.nombre == null || form.nombre.isBlank() ||
                form.descripcion == null || form.descripcion.isBlank() ||
                form.precio == null ||
                form.cantidadDisponible == null ||
                form.distrito == null || form.distrito.isBlank() ||
                form.categoria == null || form.categoria.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(MensajeProducto.ERROR_CAMPOS_OBLIGATORIOS).build();
        }


        try {
            File archivo = convertirInputStreamAFile(form.imagen);
            String urlImagen = cloudinaryService.subirImagen(archivo);

            Productos producto = new Productos();
            Instant ahora = Instant.now();
            producto.setCreadoEn(ahora);
            producto.setActualizadoEn(ahora);

            producto.setNombre(form.nombre);
            producto.setDescripcion(form.descripcion);
            producto.setPrecio(form.precio);
            producto.setCantidadDisponible(form.cantidadDisponible);
            producto.setDistrito(form.distrito);
            producto.setCategoria(form.categoria);
            producto.setImagenUrl(urlImagen);

            productosService.getRepository().persist(producto);
            return Response.status(Response.Status.CREATED).entity(producto).build();

        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(MensajeProducto.ERROR_SUBIDA_IMAGEN + e.getMessage()).build();
        }
    }


    public File convertirInputStreamAFile(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException(MensajeProducto.ERROR_INPUTSTREAM_NULL);
        }

        File tempFile = File.createTempFile("upload-", ".tmp");
        tempFile.deleteOnExit();

        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }

    @PUT
    @Path("/actualizar/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response actualizarProducto(@PathParam("id") Long id, @MultipartForm ProductoForm form) {
        Productos producto = productosService.getRepository().findById(id);
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(MensajeProducto.PRODUCTO_NO_ENCONTRADO).build();
        }
        try {
            if (form.imagen != null) {
                File archivo = convertirInputStreamAFile(form.imagen);
                String urlImagen = cloudinaryService.subirImagen(archivo);
                producto.setImagenUrl(urlImagen);
            }

            if (form.nombre != null && !form.nombre.isBlank()) {
                producto.setNombre(form.nombre);
            }
            if (form.descripcion != null && !form.descripcion.isBlank()) {
                producto.setDescripcion(form.descripcion);
            }
            if (form.precio != null) {
                producto.setPrecio(form.precio);
            }
            if (form.cantidadDisponible != null) {
                producto.setCantidadDisponible(form.cantidadDisponible);
            }
            if (form.distrito != null && !form.distrito.isBlank()) {
                producto.setDistrito(form.distrito);
            }
            if (form.categoria != null && !form.categoria.isBlank()) {
                producto.setCategoria(form.categoria);
            }

            producto.setActualizadoEn(Instant.now());

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", MensajeProducto.PRODUCTO_ACTUALIZADO);
            respuesta.put("producto", producto);

            return Response.ok(respuesta).build();

        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(MensajeProducto.ERROR_SUBIDA_IMAGEN + e.getMessage()).build();
        }
    }


    @DELETE
    @Path("/eliminar/{id}")
    @Transactional
    public Response eliminarProducto(@PathParam("id") Long id) {
        Productos producto = productosService.getRepository().findById(id);
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Producto no encontrado").build();
        }
        productosService.getRepository().delete(producto);
        return Response.ok(MensajeProducto.PRODUCTO_ELIMINADO).build();
    }

    @GET
    @Path("buscar/{id}")
    @PermitAll
    public Response obtenerProductoPorId(@PathParam("id") Long id) {
        Productos producto = productosService.getRepository().findById(id);
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(MensajeProducto.PRODUCTO_NO_ENCONTRADO).build();
        }
        ProductosDto productoDto = new ProductosDto(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidadDisponible(),
                producto.getDistrito(),
                producto.getCategoria(),
                producto.getImagenUrl(),
                producto.getCreadoEn(),
                producto.getActualizadoEn()
        );
        return Response.ok(productoDto).build();
    }
}