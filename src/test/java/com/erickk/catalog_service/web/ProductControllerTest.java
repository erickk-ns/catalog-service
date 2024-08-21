package com.erickk.catalog_service.web;

import com.erickk.catalog_service.domain.ProductResponse;
import com.erickk.catalog_service.domain.ProductService;
import com.erickk.catalog_service.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @BeforeEach
    void setup() {
        createProduct();
    }

    private String json;
    List<ProductResponse> list = new ArrayList<>();
    ProductResponse productResponse;


    @Test
    void shouldListAllWhenListAllProduct() throws Exception {
        when(productService.listAllProduct()).thenReturn(list);

        String json = """
                [
                    {"id":1,"name":"Galaxy S24","modelo":"Galaxy","marca":"Samsung","preco":3800,"descricao":"Samsung Top"},
                    {"id":2,"name":"Iphone 15","modelo":"Plus","marca":"Aplle","preco":4800,"descricao":"Iphone Top"}
                ]
                """;

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk()).andExpect(content().json(json));

    }

    @Test
    void shouldRemoveWhenGivenProductRemove() throws Exception {
        doNothing().when(productService).removeProduct(anyLong());

        mockMvc.perform(delete("/products/delete/" + 1L))
                .andExpect(status().isNoContent());

    }


    @Test
    void shouldReturnBadRequestWhenRemoveProductNotFound() throws Exception {
        doThrow(new ProductNotFoundException(1L)).when(productService).removeProduct(anyLong());

        mockMvc.perform(delete("/products/delete/" + 1L))
                .andExpect(status().isNotFound());

    }


    private void createProduct() {

        list = List.of(new ProductResponse(1L, "Galaxy S24", "Galaxy", "Samsung", new BigDecimal("3800"),
                        "Samsung Top"
                ),
                new ProductResponse(2L, "Iphone 15", "Plus", "Aplle",
                        new BigDecimal("4800"), "Iphone Top")
        );

        productResponse = new ProductResponse(1L, "Galaxy S25", "Galaxy Plus", "Samsung", new BigDecimal("5800"),
                "Samsung Top");

        json = """
                [
                    {"id":1,"name":"Galaxy S25","modelo":"Galaxy Plus","marca":"Samsung","preco":5800,"descricao":"Samsung Top"},
                ]
                """;
    }
}
