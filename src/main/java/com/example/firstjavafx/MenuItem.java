package com.example.firstjavafx;

import java.util.*;
import java.util.stream.Collectors;

public class MenuItem {
    private String name;
    private double price;
    private String category;
    private boolean availability;
    private List<String> reviews = new ArrayList<>();
    private double sold;
    private double quantity;

    public MenuItem(String name, double price, String category, boolean availability, double quantity) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.availability = availability;
        this.sold = 0;
        this.quantity = quantity;
    }
    public MenuItem(String name, double price){
        this.name = name;
        this.price = price;
    }

    public void setPrice(double price) { this.price = price; }
    public void setAvailability(boolean availability) { this.availability = availability; }
    public void setQuantity(double quantity){this.quantity = quantity;}

    public String getName(){return this.name;}
    public Double getPrice(){return this.price;}
    public Double getSold(){return this.sold;}
    public List<String> getReviews(){return this.reviews;}
    public boolean getAvailablity(){ return this.availability;}
    public String getCategory(){return this.category;}
    public double getQuantity(){return this.quantity;}

    public void addReview(String review) {
        reviews.add(review);
    }

    public void incrementSold(){
        sold++;
        quantity--;
    }
    public void decrementSold(){
        sold--;
        quantity++;
    }
    public String toString() {
        return name + " - $" + price + " [" + category + "] " + (availability ? "Available" : "Not Available");
    }

}
