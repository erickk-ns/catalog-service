package com.erickk.catalog_service.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> listAll();

    boolean existByName(String name);
}
