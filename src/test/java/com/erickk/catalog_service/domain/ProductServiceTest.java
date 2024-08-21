package com.erickk.catalog_service.domain;

import com.erickk.catalog_service.exceptions.ProductAlreadyExist;
import com.erickk.catalog_service.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
    void shouldThrowProductAlreadyExistWhenProductAlreadyExist() {
        when(productRepository.existByName(list.getLast().name())).thenReturn(true);

        assertThatExceptionOfType(ProductAlreadyExist.class)
                .isThrownBy(() -> productService.addProduct(list.getLast()));

        verify(productRepository, never()).save(any());

    }

    @Test
    void shouldSaveProductsWhenGiveAddProduct() {
        when(productRepository.save(any())).thenReturn(list.getFirst());
        var productAdd = productService.addProduct(list.getFirst());

        assertThat(productAdd).isEqualTo(productResponse);
        assertEquals(productResponse, productAdd);
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenEditDetailsProductDoesNotExist() {
        when(productRepository.findById(anyLong())).thenThrow(ProductNotFoundException.class);

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> productService.editDetailsProduct(8L, list.getFirst()));

        verifyNoMoreInteractions(productRepository);

    }

    @Test
    void shouldUpdateProductsWhenEditDetailsProduct() {

        var product = new Product(1L, "Galaxy S25", "Galaxy Plus", "Samsung", new BigDecimal("5800"),
                "Samsung Top", null, null, 0);

        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(list.getFirst()));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        var prodUpdate = productService.editDetailsProduct(1L, product);

        assertThat(prodUpdate).isNotEqualTo(list.getFirst());
        assertThat(prodUpdate.id()).isEqualTo(list.getFirst().id());
        assertThat(prodUpdate.name()).isNotEqualTo(list.getFirst().name());


    }

    @Test
    void shouldListProductsWhenProductFindAll() {
        when(productRepository.findAll()).thenReturn(list);

        int sizeList = productService.listAllProduct().size();

        assertThat(sizeList).isEqualTo(2);
    }

    @Test
    void shouldOneProductWhenGiveGetById() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(list.getFirst()));

        var productId = productService.getById(1L);
        assertThat(productId).isEqualTo(productResponse);
        assertThat(productId.name()).isEqualTo(productResponse.name());
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenProductfindByIdDoesNotExist() {
        when(productRepository.findById(anyLong())).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class,
                () -> productService.getById(8L));

        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldThThrowProductNotFoundExceptionWhenRemoveProductDoesNotFind() {
        when(productRepository.existsById(anyLong())).thenReturn(false);

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> productService.removeProduct(1L));
    }

    @Test
    void shouldDeleteWhenRemoveProduct() {
        when(productRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(productRepository).deleteById(anyLong());

        productService.removeProduct(1L);

        verify(productRepository).deleteById(1L);
        verifyNoMoreInteractions(productRepository);
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