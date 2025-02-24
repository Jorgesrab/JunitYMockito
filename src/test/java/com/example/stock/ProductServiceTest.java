package com.example.stock;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductServiceTest {
    private ProductService productService;

    @BeforeEach
    public void setup() {
        productService = new ProductService();
    }

    @Test
    public void testAddProduct() {
        Product product = new Product("Producto1", 10, 100.0);
        productService.addProduct(product);
        assertEquals(1, productService.getAllProducts().size());
    }

    @Test
    public void testAddNullProduct() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.addProduct(null);
        });
        String expectedMessage = "Producto no puede ser nulo";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product("Producto1", 10, 100.0);
        productService.addProduct(product);
        Product updatedProduct = new Product("Producto1 actualizado", 5, 90.0);
        productService.updateProduct(0, updatedProduct);
        assertEquals("Producto1 actualizado", productService.getAllProducts().get(0).getName());
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product("Producto1", 10, 100.0);
        productService.addProduct(product);
        productService.deleteProduct(0);
        assertEquals(0, productService.getAllProducts().size());
    }

    @Test
    public void testInvalidIndexUpdate() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            productService.updateProduct(0, new Product("No existe", 1, 10.0));
        });
        String expectedMessage = "Índice de producto inválido";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
