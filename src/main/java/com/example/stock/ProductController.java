package com.example.stock;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ProductController {
    private ProductService productService = new ProductService();

    // Elementos para la vista de errores y tabla
    @FXML
    private Label errorLabel;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Void> actionColumn;  // Columna para botón de eliminar

    // Elementos del formulario para agregar producto
    @FXML
    private TextField nameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField priceField;

    // Paneles de vista
    @FXML
    private VBox viewProductsPane;
    @FXML
    private VBox addProductPane;

    @FXML
    public void initialize() {
        // Configuración de las columnas de la tabla
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Agrega el botón de eliminación a cada fila
        addDeleteButtonToTable();

        // Muestra inicialmente la vista de productos
        showProductsView();
        clearError();
    }

    // Muestra la vista de productos (tabla)
    private void showProductsView() {
        viewProductsPane.setVisible(true);
        addProductPane.setVisible(false);
        refreshProductTable();
    }

    // Acción del botón "Ver Productos"
    @FXML
    public void handleViewProducts(ActionEvent event) {
        showProductsView();
        clearError();
    }

    // Acción del botón "Agregar Producto"
    @FXML
    public void handleAddProductView(ActionEvent event) {
        viewProductsPane.setVisible(false);
        addProductPane.setVisible(true);
        clearFields();
        clearError();
    }

    // Agrega un producto y actualiza la vista
    @FXML
    public void addProduct(ActionEvent event) {
        clearError();
        String name = nameField.getText();
        // Validar que el nombre no esté vacío
        if(name == null || name.trim().isEmpty()){
            showError("El nombre no puede estar vacío");
            return;
        }

        int quantity;
        double price;

        // Validación separada para la cantidad
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            showError("Error en la cantidad: debe ser un número entero");
            return;
        }

        // Validación separada para el precio
        try {
            price = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            showError("Error en el precio: debe ser un número válido (decimal)");
            return;
        }

        try {
            // El constructor de Product ya válída si la cantidad o el precio son negativos
            Product product = new Product(name, quantity, price);
            productService.addProduct(product);
            showProductsView();
            clearFields();
        } catch (IllegalArgumentException e) {
            // Aquí se capturan errores como cantidad o precio negativos
            showError(e.getMessage());
        }
    }


    // Refresca el contenido de la tabla con la lista actual de productos
    private void refreshProductTable() {
        productTableView.setItems(FXCollections.observableArrayList(productService.getAllProducts()));
    }

    // Limpia los campos del formulario
    private void clearFields() {
        nameField.clear();
        quantityField.clear();
        priceField.clear();
    }

    // Muestra un mensaje de error en la etiqueta
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    // Limpia y oculta el mensaje de error
    private void clearError() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }

    // Agrega una columna con un botón para eliminar cada producto
    private void addDeleteButtonToTable() {
        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    private final Button deleteButton = new Button("Eliminar");

                    {
                        deleteButton.setOnAction((ActionEvent event) -> {
                            Product product = getTableView().getItems().get(getIndex());
                            productService.deleteProduct(product);
                            refreshProductTable();
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
                return cell;
            }
        };
        actionColumn.setCellFactory(cellFactory);
    }
}
