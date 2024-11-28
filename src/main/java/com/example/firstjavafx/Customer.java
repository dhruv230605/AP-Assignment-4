package com.example.firstjavafx;

import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class Customer {
    private String name;
    private boolean isVIP;
    private List<Order> orderHistory = new ArrayList<>();
    private List<MenuItem> cart = new ArrayList<>();
    private String payment;
    private String delivery;
    private String email;
    private String password;

    public Customer(String name, boolean isVIP, String email, String password) {
        this.name = name;
        this.isVIP = isVIP;
        this.email = email;
        this.password = password;
        loadOrderHistory();
    }
    public String getName() {return this.name;}
    public String getPassword(){return this.password;}
    public String getEmail(){return this.email;}
    public List<MenuItem> getCart(){return this.cart;}
    public boolean getisVIP(){return this.isVIP;}
    public void addOrder(Order o){orderHistory.add(o);}

    // Browse Menu
    public void viewMenu(List<MenuItem> menu, Admin admin) {
        for (MenuItem i : menu){
//            System.out.println("Name: " + i.getName() + "," + "Price: " + i.getPrice() + "," + "Availablity: " + i.getAvailablity());
            System.out.println(i);
        }
    }

    public void searchMenu(List<MenuItem> menu, String keyword) {
        for (MenuItem item : menu) {
            if (item.getName().contains(keyword)) {
                System.out.println(item);
            }
        }
    }

    // Cart Operations
    public void addItemToCart(MenuItem item, int quantity) {
        for (int i = 0; i < quantity; i++){
            cart.add(item);
            item.incrementSold();
        }
    }

    public void removeItemFromCart(MenuItem item) {
        cart.remove(item);
        item.decrementSold();
    }
    public void removeItemFromCart(MenuItem item, int quantity) {
        for (int i = 0; i<quantity; i++){
            cart.remove(item);
            item.decrementSold();
        }
    }

    public Double getTotal(){
        double cost = (double)0;
        for (MenuItem item : cart){
            cost += item.getPrice();
        }
        return cost;
    }

    public void checkout(String payment, String delivery, List<MenuItem> items, boolean vip) {
        this.payment = payment;
        this.delivery = delivery;
        orderHistory.add(new Order(items, vip));
        saveOrderHistory();
        System.out.println("Order Completed!");
    }


    // Order Tracking
    public void viewOrderStatus(int orderId) {
        for (Order order : orderHistory){
            if (order.getId().equals(orderId)){
                System.out.println("Order Status: " + order.getStatus());
            }
        }
    }

    public void cancelOrder(int orderId) {
        for (Order order : orderHistory){
            if (order.getId().equals(orderId)){
                order.setStatus("Cancelled");
                System.out.println("Order Status: " + order.getStatus());
            }
        }
    }

    public void orderHistory(){
        for (Order order : orderHistory){
            System.out.print(order.getId() + ":");
            order.orderDetails();
            System.out.println();
        }
    }

    // Reviews
    public void leaveReview(MenuItem item, String review) {
        item.addReview(review);
    }

    public void viewReviews(MenuItem item){
        for (String review : item.getReviews()){
            System.out.println(review);
        }
    }

    public void saveOrderHistory() {
        String fileName = "orderhistory_" + email + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Order order : orderHistory) {
                bw.write(order.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadOrderHistory() {
        String fileName = "orderhistory_" + email + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                orderHistory.add(Order.fromFileString(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous order history found for " + email);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
