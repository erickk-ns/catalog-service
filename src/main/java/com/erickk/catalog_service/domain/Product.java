package com.erickk.catalog_service.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record Product(

        Long id,
        String name,
        String modelo,
        String marca,
        BigDecimal preco,
        String descricao,
        Instant created_date,
        Instant last_modified_date,
        int version

) {

    public static Product of(Product p) {
        return new Product(p.id, p.name, p.modelo, p.marca, p.preco, p.descricao,
                null, null, 0);
    }

}
