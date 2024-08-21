package com.erickk.catalog_service.exceptions;

public class ProductListEmptyException extends RuntimeException {

    public ProductListEmptyException() {
        super("A lista não pode estar vazia.");
    }

}