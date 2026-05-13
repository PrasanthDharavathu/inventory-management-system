package com.example.inventory.dto;

public class ProductResponseDTO {

    private Long id;
    private String name;
    private String category;
    private double price;

    public ProductResponseDTO(Long id, String name, String category, double price){
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getCategory(){
        return category;
    }
    public double getPrice(){
        return price;
    }
}
