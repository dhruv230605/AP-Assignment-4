package com.example.firstjavafx;

import com.example.firstjavafx.Customer;

import java.io.*;
import java.util.HashMap;

public class UserManager {
    private static final String FILE_NAME = "customers.txt";

    public static HashMap<String, Customer> loadCustomers() {
        HashMap<String, Customer> customers = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    String name = parts[0];
                    boolean isVIP = Boolean.parseBoolean(parts[1]);
                    String email = parts[2];
                    String password = parts[3];
                    customers.put(email, new Customer(name, isVIP, email, password));
                }
            }
        }
        catch (FileNotFoundException e) {

            System.out.println("No existing customer data found");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static void saveCustomers(HashMap<String, Customer> customers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Customer customer : customers.values()) {
                bw.write(customer.getName() + ";" + customer.getisVIP() + ";" + customer.getEmail() + ";" + customer.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
