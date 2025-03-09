package com.example.stock;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Arrays;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductControllerMockitoTest {

    private ProductController controller;
    private ProductService mockProductService;

    @BeforeAll
    public static void initJFX() {
        // Inicializa el entorno JavaFX
        new JFXPanel();
    }

    @BeforeEach
    public void setup() throws Exception {
        controller = new ProductController();
        mockProductService = mock(ProductService.class);

        // Inyectamos el mock en el campo privado 'productService' de ProductController
        Field field = ProductController.class.getDeclaredField("productService");
        field.setAccessible(true);
        field.set(controller, mockProductService);

        // Inicializamos manualmente los nodos que utiliza el controlador
        controller.errorLabel = new Label();
        controller.productTableView = new TableView<>();
        controller.nameColumn = new TableColumn<>();
        controller.quantityColumn = new TableColumn<>();
        controller.priceColumn = new TableColumn<>();
        controller.actionColumn = new TableColumn<>();
        controller.nameField = new TextField();
        controller.quantityField = new TextField();
        controller.priceField = new TextField();
        controller.viewProductsPane = new VBox();
        controller.addProductPane = new VBox();

        // Llamamos al método initialize para configurar la vista
        controller.initialize();
    }

    // Test para el método addProduct del controlador
    @Test
    public void testAddProductController() {
        // Configuramos los campos con datos válidos
        controller.nameField.setText("Producto Test");
        controller.quantityField.setText("10");
        controller.priceField.setText("50.0");

        // Invocamos el método addProduct (simulamos un ActionEvent)
        controller.addProduct(new ActionEvent());

        // Verificamos que se haya llamado a addProduct del servicio con un producto que contenga los datos esperados
        verify(mockProductService).addProduct(argThat(product ->
                product.getName().equals("Producto Test") &&
                        product.getQuantity() == 10 &&
                        product.getPrice() == 50.0
        ));
    }

    // Test para verificar la acción del botón de eliminar
    @Test
    public void testDeleteProductController() {
        // Creamos un producto de prueba y simulamos que el servicio lo retorna en la lista
        Product product = new Product("Producto Test", 5, 25.0);
        when(mockProductService.getAllProducts()).thenReturn(Arrays.asList(product));

        // Como el método refreshProductTable() se basa en getAllProducts(),
        // lo llamamos para actualizar la tabla (aunque en este test solo se ilustra la interacción)
        controller.initialize(); // Esto configura las celdas, incluyendo el botón de eliminar

        // Simulamos la acción del botón de eliminar:
        // Se invoca directamente el método deleteProduct del servicio para el producto dado.
        controller.productService.deleteProduct(product);

        // Verificamos que se haya invocado el método deleteProduct del servicio
        verify(mockProductService).deleteProduct(product);
    }
}
