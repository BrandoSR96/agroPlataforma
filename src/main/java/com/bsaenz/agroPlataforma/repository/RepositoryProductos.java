package com.bsaenz.agroPlataforma.repository;

import com.bsaenz.agroPlataforma.model.Productos;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RepositoryProductos implements PanacheRepository<Productos> {
}
