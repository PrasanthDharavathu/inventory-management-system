package com.example.inventory.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@NotBlank(message = "Name cannot be blank")
    private String name;
@NotBlank(message = "Category cannot be blank")
    private String category;
@Positive(message = "Price must be greater than 0")
    private double price;
@Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;
    //Getter for id
    public Long getId(){
        return id;
    }
        public void setId(long id) {
        this.id = id;
    }
    //Getter for name
    public String getName(){
        return name;
    }
    //Setter for name
    public void setName(String name){
        this.name = name;
    }
    //Getter and Setter for category
    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }
    //Getter and setter for price
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
    //Getter and setter for Quantity
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }


}
