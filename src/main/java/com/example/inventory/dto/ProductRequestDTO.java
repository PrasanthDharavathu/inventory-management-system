package com.example.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ProductRequestDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Category cannot be blank")
    private String category;
    @Positive(message = "Price must be greater than 0")
    private double price;
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

}
