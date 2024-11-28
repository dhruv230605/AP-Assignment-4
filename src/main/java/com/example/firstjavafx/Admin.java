package com.example.firstjavafx;

import javafx.stage.Stage;

import java.util.*;

public class Admin {
    private List<MenuItem> menu = new ArrayList<>();
    private PriorityQueue<Order> orders = new PriorityQueue<>(new OrderComparator());
    private String password;

    public PriorityQueue<Order> getOrders(){
        return orders;
    }


    public Admin(String password){
        this.password = password;
    }

    public List<MenuItem> getMenu(){
        return this.menu;
    }
    public void addMenu(MenuItem m){
        menu.add(m);
    }
    public void addOrder(Order o){
        orders.add(o);
    }

    // Menu Management
    public void viewItem(MenuItem i){
        System.out.println("Name: " + i.getName() + "," + "Price: " + i.getPrice() + "," + "Availablity: " + i.getAvailablity());
    }

    public void viewAllItems(){
        for (MenuItem i : menu){
            System.out.println("Name: " + i.getName() + "," + "Price: " + i.getPrice() + "," + "Availablity: " + i.getAvailablity());
        }
    }

    public void addMenuItem(String name, double price, String category, boolean availability, double quantity) {
        menu.add(new MenuItem(name, price, category, availability, quantity));
    }

    public void updateMenuItem(String name, double price, boolean availability) {
        for (MenuItem item : menu) {
            if (item.getName().equals(name)) {
                item.setPrice(price);
                item.setAvailability(availability);
                break;
            }
        }
    }

    public void removeMenuItem(String name) {
        menu.removeIf(item -> item.getName().equals(name));
        updatePendingOrders(name);
    }

    private void updatePendingOrders(String name) {
        for (Order order : orders) {
            order.denyIfContains(name);
        }
    }

    // Order Management
    public void viewPendingOrders() {
        for (Order order : orders) {
            if (!order.getStatus().equals("Denied")){ //Only printing orders that are not denied
                System.out.println(order);
            }
        }
    }

    public void updateOrderStatus(int orderId, String status) {
        for (Order order : orders){
            if (order.getId().equals(orderId)){
                order.setStatus(status);
            }
        }
    }

    public void processRefund(int orderId) {
        System.out.println("Refund initiated for Order ID: " + orderId);
        System.out.println("Thank you!");
    }

    public void handleSpecialRequests(int orderId, String request) {
        for (Order order : orders){
            if (order.getId().equals(orderId)){
                order.setSpecialRequest(request);
            }
        }
    }

    // Report Generation
    public void generateDailySalesReport()
    {
        System.out.println("Daily Sales Report");
        if (orders.size() < 1){
            System.out.println("No Orders Yet");
            return;
        }

        //Total Sales
        Double totalSales = (double) 0;
        for (Order order : orders){
            totalSales += order.costOfOrder();
        }
        System.out.println("Total Sales: " + totalSales);

        //Most popular items
        //print 3 items with highest sold values
        menu.sort(new Comparator<MenuItem>() {
            @Override
            public int compare(MenuItem m1, MenuItem m2) {
                return Double.compare(m2.getSold(), m1.getSold());
            }
        });
        System.out.println("Most popular items");
        for (int i = 1; i<4; i++){
            System.out.println(i + ". " + menu.get(i).getName()); //+ ": " + menu.get(i).getSold()
        }

        //Total orders
        System.out.println("Total orders: " + orders.size());
    }
}
