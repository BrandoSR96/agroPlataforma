package com.bsaenz.agroplataforma.repository;

import com.bsaenz.agroplataforma.model.Productos;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RepositoryProductos implements PanacheRepository<Productos> {
}
