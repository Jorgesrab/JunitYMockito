package com.example.stock;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SecurityTests {
    @Test
    public void testEmptyName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("", 5, 50.0);
        });
        String expectedMessage = "El nombre no puede estar vacío";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testNegativeQuantity() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("Producto", -1, 50.0);
        });
        String expectedMessage = "La cantidad no puede ser negativa";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testNegativePrice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("Producto", 5, -10.0);
        });
        String expectedMessage = "El precio no puede ser negativo";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
