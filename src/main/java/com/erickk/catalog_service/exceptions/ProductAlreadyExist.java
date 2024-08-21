package com.erickk.catalog_service.exceptions;

public class ProductAlreadyExist extends RuntimeException {

    public ProductAlreadyExist(String name) {
        super("Produto com o nome: " + name + ", já existe.");
    }

}