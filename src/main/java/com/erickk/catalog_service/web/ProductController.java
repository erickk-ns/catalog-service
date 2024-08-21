package com.erickk.catalog_service.web;

import com.erickk.catalog_service.domain.Product;
import com.erickk.catalog_service.domain.ProductResponse;
import com.erickk.catalog_service.domain.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> findAllProduct() {
        return productService.listAllProduct();
    }

    @PutMapping("/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse editProductById(@PathVariable("id") @Valid Long id, @RequestBody Product product) {
        return productService.editDetailsProduct(id, product);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProduct(@PathVariable("id") final Long id) {
        productService.removeProduct(id);
    }

}
