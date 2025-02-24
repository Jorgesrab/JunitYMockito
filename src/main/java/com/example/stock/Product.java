package com.example.stock;

public class Product {
    private String name;
    private int quantity;
    private double price;

    public Product(String name, int quantity, double price) {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if(quantity < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        if(price < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters y setters con validaciones
    public String getName() { return name; }
    public void setName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.name = name;
    }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        if(quantity < 0){
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        this.quantity = quantity;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if(price < 0){
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.price = price;
    }
}
