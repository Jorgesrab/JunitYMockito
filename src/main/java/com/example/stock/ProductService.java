package com.example.stock;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private List<Product> productList = new ArrayList<>();

    public void addProduct(Product product) {
        if(product == null) {
            throw new IllegalArgumentException("Producto no puede ser nulo");
        }
        productList.add(product);
    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public void updateProduct(int index, Product updatedProduct) {
        if(index < 0 || index >= productList.size()) {
            throw new IndexOutOfBoundsException("Índice de producto inválido");
        }
        productList.set(index, updatedProduct);
    }

    public void deleteProduct(int index) {
        if(index < 0 || index >= productList.size()) {
            throw new IndexOutOfBoundsException("Índice de producto inválido");
        }
        productList.remove(index);
    }

    public void deleteProduct(Product product) {
        productList.remove(product);
    }

}
