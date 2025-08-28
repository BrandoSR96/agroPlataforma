package com.bsaenz.agroPlataforma.service;

import com.bsaenz.agroPlataforma.repository.RepositoryProductos;
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
