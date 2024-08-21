package com.erickk.catalog_service.domain;

import java.math.BigDecimal;

public record ProductResponse(

        Long id,
        String name,
        String modelo,
        String marca,
        BigDecimal preco,
        String descricao

) {

    public static ProductResponse fromEntityToDto(Product product) {
        return new ProductResponse(product.id(), product.name(), product.modelo(),
                product.marca(), product.preco(), product.descricao());
    }

}