package com.bsaenz.agroplataforma.service;

import com.bsaenz.agroplataforma.repository.RepositoryProductos;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductosService {

    @Inject
    private RepositoryProductos repositoryProductos;

    public RepositoryProductos getRepository() {
        return repositoryProductos;
    }
}
