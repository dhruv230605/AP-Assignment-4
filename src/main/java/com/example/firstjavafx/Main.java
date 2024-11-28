package com.example.firstjavafx;

import javafx.application.Application;

import javax.swing.*;
import java.util.*;

public class Main {
    public static HashMap<String, Customer> customers = new HashMap<>();
    public static void main(String[] args) {
        customers = UserManager.loadCustomers();
        Admin admin = new Admin("iiitdadmin");
        Customer c1 = new Customer("Dhruv", true, "d", "d");
        Customer c2 = new Customer("Alex", false, "a", "a");
        Customer c3 = new Customer("Betty", true, "b", "b");
        customers.put(c1.getEmail(), c1);
        customers.put(c2.getEmail(), c2);
        customers.put(c3.getEmail(), c3);
        MenuItem m1 = new MenuItem("Poha", 20.00, "Breakfast", true, 30.00);
        MenuItem m2 = new MenuItem("Samosa", 15.00, "Snack", true, 25.00);
        MenuItem m3 = new MenuItem("Vada Pav", 25.00, "Snack", true, 35.00);
        MenuItem m4 = new MenuItem("Pakora", 30.00, "Snack", true, 40.00);
        MenuItem m5 = new MenuItem("Bhel Puri", 20.00, "Snack", true, 30.00);
        MenuItem m6 = new MenuItem("Kachori", 18.00, "Snack", true, 28.00);
        MenuItem m7 = new MenuItem("Dhokla", 25.00, "Breakfast", true, 35.00);
        MenuItem m8 = new MenuItem("Aloo Tikki", 20.00, "Snack", true, 30.00);
        MenuItem m9 = new MenuItem("Chole Bhature", 50.00, "Lunch", true, 70.00);
        MenuItem m10 = new MenuItem("Pav Bhaji", 40.00, "Lunch", true, 60.00);
        MenuItem m11 = new MenuItem("Idli Sambar", 35.00, "Breakfast", true, 50.00);
        MenuItem m12 = new MenuItem("Dosa", 45.00, "Breakfast", true, 65.00);
        admin.addMenu(m1);admin.addMenu(m2); admin.addMenu(m3); admin.addMenu(m4); admin.addMenu(m5); admin.addMenu(m6); admin.addMenu(m7); admin.addMenu(m8); admin.addMenu(m9); admin.addMenu(m10); admin.addMenu(m11); admin.addMenu(m12);
        List<MenuItem> l1 = new ArrayList<MenuItem>();
        l1.add(m1);l1.add(m2);l1.add(m3);l1.add(m4);
        List<MenuItem> l2 = new ArrayList<MenuItem>();
        l2.add(m5);l2.add(m6);l2.add(m7);l2.add(m8);
        List<MenuItem> l3 = new ArrayList<MenuItem>();
        l3.add(m9);l3.add(m10);l3.add(m11);l3.add(m12);
        Order o1 = new Order(l1, true);
        Order o2 = new Order(l2, false);
        Order o3 = new Order(l3, true);
        admin.addOrder(o1);admin.addOrder(o2);admin.addOrder(o3);
        c1.addOrder(o1);
        c2.addOrder(o2);
        c3.addOrder(o3);

        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("BYTE ME!");
            System.out.println("1. Enter the Application");
            System.out.println("2. Exit the Application");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    handleUserSelection(sc, admin);
                    break;
                case 2:
                    exit = true;
                    System.out.println("Exit");
                    UserManager.saveCustomers(customers);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        sc.close();
    }

    private static void handleUserSelection(Scanner sc, Admin admin){
        System.out.println("Select your role:");
        System.out.println("1. Admin");
        System.out.println("2. Customer");
        System.out.print("Choose an option: ");
        int role = sc.nextInt();
        sc.nextLine();

        switch (role) {
            case 1:
                handleAdmin(sc, admin);
                break;
            case 2:
                handleCustomer(sc, admin);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
    private static void handleAdmin(Scanner sc, Admin admin) {
        System.out.print("Enter admin password: ");
        String password = sc.nextLine();


        //Exception Handling for Invalid Login
        try{
            if(!"iiitdadmin".equals(password)){
                throw new InvalidLoginException("Incorrect admin password");
            }
            System.out.println("Welcome, Administrator.");
            adminHomePage(sc, admin);
        }
        catch(InvalidLoginException e){
            System.out.println(e.getMessage());
        }
    }

    private static void handleCustomer(Scanner sc, Admin admin) {
        System.out.println("1. Sign Up");
        System.out.println("2. Login");
        System.out.print("Choose an option: ");
        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1:
                signUpCustomer(sc);
                break;
            case 2:
                loginCustomer(sc, admin);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void signUpCustomer(Scanner sc) {
        System.out.println("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        if (customers.containsKey(email)) {
            System.out.println("Email already registered. Please log in.");
            return;
        }

        Customer s1 = new Customer(name, true, email, password);
        customers.put(email, s1);
        UserManager.saveCustomers(customers);
        System.out.println("Customer registration successful.");
    }

    private static void loginCustomer(Scanner sc, Admin admin) {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        //Exception Handling for Invalid Login
        try{
            if (!customers.containsKey(email)){
                throw new InvalidLoginException("Invalid email");
            }
            Customer customer = customers.get(email);
            if (customer.getPassword().equals(password)){
                System.out.println("Welcome, You have successfully logged in.");
                customerHomePage(sc, customer, admin);
            }
            else {
                throw new InvalidLoginException("Invalid password");
            }
        }
        catch(InvalidLoginException e){
            System.out.println(e.getMessage());
        }
    }

    private static void customerHomePage(Scanner sc, Customer customer, Admin admin) {
        while (true) {
            System.out.println("1. Browse Menu");
            System.out.println("2. Cart Operations");
            System.out.println("3. Order Tracking");
            System.out.println("4. Item Reviews");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    browseMenu(sc, customer, admin);
                    break;
                case 2:
                    cartOperations(sc, customer, admin);
                    break;
                case 3:
                    orderTracking(sc, customer, admin);
                    break;
                case 4:
                    itemReviews(sc, customer, admin);
                    break;
                case 5:
                    System.out.println("Logging out");
                    return;
                default:
                    System.out.println("Invalid.");
            }
        }
    }

    private static void orderTracking(Scanner sc, Customer customer, Admin admin) {
        while (true) {
            System.out.println("1. View Order Status");
            System.out.println("2. Cancel Order");
            System.out.println("3. Order History");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter order ID:");
                    int id = sc.nextInt();
                    sc.nextLine();
                    customer.viewOrderStatus(id);
                    break;
                case 2:
                    System.out.println("Enter order ID:");
                    int id1 = sc.nextInt();
                    sc.nextLine();
                    customer.cancelOrder(id1);
                    break;
                case 3:
                    System.out.println("Order History");
                    customer.orderHistory();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid.");
            }
        }
    }

    private static void itemReviews(Scanner sc, Customer customer, Admin admin) {
        while (true) {
            System.out.println("1. Provide review");
            System.out.println("2. View reviews");
            System.out.println("3. Back");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Name of item to review: ");
                    String name2 = sc.nextLine();
                    System.out.println("Enter review:");
                    String review = sc.nextLine();
                    for (MenuItem item : admin.getMenu()){
                        if (item.getName().equals(name2)){
                            customer.leaveReview(item, review);
                        }
                    }
                    break;
                case 2:
                    System.out.println("Name of item to see review of: ");
                    String name20 = sc.nextLine();
                    for (MenuItem item : admin.getMenu()){
                        if (item.getName().equals(name20)){
                            customer.viewReviews(item);
                        }
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid.");
            }
        }
    }

    private static void cartOperations(Scanner sc, Customer customer, Admin admin) {
        while (true) {
            System.out.println("1. Add items");
            System.out.println("2. Modify Quantity");
            System.out.println("3. Remove Items");
            System.out.println("4. View Total");
            System.out.println("5. Checkout");
            System.out.println("6. Back");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Name of item to add: ");
                    String name2 = sc.nextLine();
                    System.out.println("How many pieces do you want to add? (Quantity)");
                    int quan = sc.nextInt();
                    sc.nextLine();
                    for (MenuItem item : admin.getMenu()){
                        if (item.getName().equals(name2)){
                            customer.addItemToCart(item, quan);
                        }
                    }
                    break;
                case 2:
                    System.out.println("Enter name of item: ");
                    String name45 = sc.nextLine();
                    System.out.println("Enter desired quantity: ");
                    double quan2 = sc.nextDouble();
                    sc.nextLine();
                    for (MenuItem item : customer.getCart()){
                        if (item.getName().equals(name45)){
                            if (quan2 > item.getSold()){
                                    customer.addItemToCart(item,(int)((int)quan2-item.getSold()));
                                }
                            else{
                                customer.removeItemFromCart(item, (int)(item.getSold() - (int)quan2 ));
                            }
                        }
                        break;
                    }
                    break;
                case 3:
                    System.out.println("Name of item to remove: ");
                    String name3 = sc.nextLine();
                    for (MenuItem item : admin.getMenu()){
                        if (item.getName().equals(name3)){
                            customer.removeItemFromCart(item);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Total: " + customer.getTotal());
                    break;
                case 5:
                    System.out.println("Checkout");
                    System.out.println("Please provide payment details: ");
                    String payment = sc.nextLine();
                    System.out.println("Please provide delivery details: ");
                    String delivery = sc.nextLine();
                    customer.checkout(payment, delivery, customer.getCart(), customer.getisVIP());
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid.");
            }
        }
    }

    private static void browseMenu(Scanner sc, Customer customer, Admin admin){
        while (true) {
            System.out.println("1. View all items");
            System.out.println("2. Search for item");
            System.out.println("3. Filter by category");
            System.out.println("4. Sort by price");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    //customer.viewMenu(admin.getMenu(), admin);
                    SwingUtilities.invokeLater(() -> new MenuGUI(admin));

                    break;
                case 2:
                    System.out.print("Enter item name: ");
                    String name = sc.nextLine();
                    customer.searchMenu(admin.getMenu(), name);
                    break;
                case 3:
                    System.out.print("Enter category name: ");
                    String category = sc.nextLine();
                    for (MenuItem item : admin.getMenu()){
                        if (item.getCategory().equals(category)){
                            admin.viewItem(item);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Enter A for Ascending order and D for Descending order: ");
                    String input600 = sc.nextLine();
                    if (input600.equals("D")){
                        admin.getMenu().sort(Comparator.comparing(MenuItem::getPrice).reversed());
                    }
                    else{
                        admin.getMenu().sort(Comparator.comparing(MenuItem::getPrice));
                    }
                    for (MenuItem item : admin.getMenu()) {
                        System.out.println(item);
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid.");
            }
        }
    }

    private static void adminHomePage(Scanner sc, Admin admin) {
        while (true) {
            System.out.println("\nWelcome, Admin ");
            System.out.println("1. Menu Management");
            System.out.println("2. Order Management");
            System.out.println("3. Report Generation");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    menuManagement(sc, admin);
                    break;
                case 2:
                    orderManagement(sc, admin);
                    break;
                case 3:
                    admin.generateDailySalesReport();
                    break;
                case 4:
                    System.out.println("Logging out");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void orderManagement(Scanner sc, Admin admin) {
        while (true) {
            System.out.println("1. View Pending Orders");
            System.out.println("2. Update Order Status");
            System.out.println("3. Process Refunds");
            System.out.println("4. Handle Special Requests");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    //admin.viewPendingOrders();
                    SwingUtilities.invokeLater(() -> new PendingOrdersGUI(admin));
                    break;
                case 2:
                    System.out.println("Enter Order ID:");
                    int id3 = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter updated status:");
                    String ustatus = sc.nextLine();
                    admin.updateOrderStatus(id3, ustatus);
                    break;
                case 3:
                    System.out.println("Enter Order ID:");
                    int id4 = sc.nextInt();
                    sc.nextLine();
                    admin.processRefund(id4);
                    break;
                case 4:
                    System.out.println("Enter Order ID:");
                    int id5 = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter special request:");
                    String request = sc.nextLine();
                    admin.handleSpecialRequests(id5, request);
                    break;
                case 5:
                    System.out.println("Logging out");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void menuManagement(Scanner sc, Admin admin) {
        while (true) {
            System.out.println("1. Add new items");
            System.out.println("2. Update existing items");
            System.out.println("3. Remove items");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter name: ");
                    String name30 = sc.nextLine();
                    System.out.println("Enter price: ");
                    Double price = sc.nextDouble();
                    sc.nextLine();
                    System.out.println("Enter category: ");
                    String category1 = sc.nextLine();
                    System.out.println("Enter availability: ");
                    boolean avail = sc.nextBoolean();
                    sc.nextLine();
                    System.out.println("Enter quantity: ");
                    Double quan = sc.nextDouble();
                    sc.nextLine();
                    admin.addMenuItem(name30, price, category1, avail, quan);
                    break;
                case 2:
                    System.out.println("Enter name: ");
                    String name31 = sc.nextLine();
                    System.out.println("Enter price: ");
                    Double price1 = sc.nextDouble();
                    sc.nextLine();
                    System.out.println("Enter availability: ");
                    boolean avail1 = sc.nextBoolean();
                    sc.nextLine();
                    admin.updateMenuItem(name31, price1, avail1);
                    break;
                case 3:
                    System.out.println("Enter name: ");
                    String name32 = sc.nextLine();
                    admin.removeMenuItem(name32);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid.");
            }
        }
    }
}
