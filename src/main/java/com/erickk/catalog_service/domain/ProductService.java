package com.erickk.catalog_service.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> listAllProduct() {
        return StreamSupport.
                stream(productRepository.findAll().spliterator(), false)
                .map(ProductResponse::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public ProductResponse getById(Long id) {
        return productRepository.findById(id).map(ProductResponse::fromEntityToDto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
    }


    public ProductResponse addProduct(Product product) {
        return ProductResponse.fromEntityToDto(productRepository.save(product));
    }

    public ProductResponse editDetailsProduct(Long id, Product product) {
        return productRepository.findById(id)
                .map(existProduct -> {
                    var productUpdate = Product.builder()
                            .id(existProduct.id()).name(product.name())
                            .modelo(product.modelo()).marca(product.marca())
                            .preco(product.preco()).descricao(product.descricao())
                            .created_date(existProduct.created_date())
                            .last_modified_date(existProduct.last_modified_date())
                            .version(existProduct.version())
                            .build();
                    return ProductResponse.fromEntityToDto(productRepository.save(productUpdate));
                }).orElseThrow(() -> new RuntimeException("Falha"));
    }

}
