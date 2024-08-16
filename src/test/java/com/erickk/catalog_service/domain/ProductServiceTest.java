package com.erickk.catalog_service.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    List<Product> list;
    ProductResponse productResponse;

    @BeforeEach
    void setup() {
        createProduct();
    }

    @Test
    void shouldSaveProductsWhenGiveAddProduct() {
        when(productRepository.save(any())).thenReturn(list.getFirst());
        var prodSave = productService.addProduct(list.getFirst());

        assertThat(prodSave).isEqualTo(productResponse);
        assertEquals(productResponse, prodSave);
    }

    @Test
    void shouldUpdateProductsWhenGiveEditDetailsProduct() {

        var product = new Product(1L, "Galaxy S25", "Galaxy Plus", "Samsung", new BigDecimal("5800"),
                "Samsung Top", null, null, 0);

        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(list.getFirst()));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        var prodUpdate = productService.editDetailsProduct(1L, list.getFirst());

        assertThat(product).isNotEqualTo(prodUpdate);
        assertEquals(product.id(), prodUpdate.id());
    }

    @Test
    void shouldProductsWhenGiveProductFindAll() {

        when(productRepository.findAll()).thenReturn(list);
        assertThat(productService.listAllProduct().size()).isEqualTo(2);
    }

    @Test
    void shouldOneProductWhenGiveGetById() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(list.getFirst()));
        assertThat(productService.getById(1L)).isEqualTo(productResponse);
    }

    @Test
    void shouldRuntimeExceptionWhenProductDoesNotExist() {
        when(productRepository.findById(anyLong())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> productService.getById(8L));
    }


    private void createProduct() {

        list = List.of(new Product(1L, "Galaxy S24", "Galaxy", "Samsung", new BigDecimal("3800"),
                        "Samsung Top", null, null, 0
                ),
                new Product(1L, "Iphone 15", "Plus", "Aplle",
                        new BigDecimal("4800"), "Iphone Top", null, null, 0)
        );

        productResponse = new ProductResponse(1L, "Galaxy S24",
                "Galaxy", "Samsung", new BigDecimal("3800"),
                "Samsung Top");
    }
}