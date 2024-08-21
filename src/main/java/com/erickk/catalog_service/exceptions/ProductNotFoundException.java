package com.erickk.catalog_service.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Produto com ID: " + id + " não encontrado.");
    }

}
